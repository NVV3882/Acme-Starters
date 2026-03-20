
package acme.entities.invention;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MathHelper;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidInvention;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Inventor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidInvention
public class Invention extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment()
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment()
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	//Atributos derivados
	@Transient
	@Autowired
	InventionRepository			inventionRepository;


	@Mandatory
	//@Valid
	@Transient
	public Double getMonthsActive() {
		Date fechaIni = this.startMoment;
		Date fechaFin = this.endMoment;
		if (fechaIni != null && fechaFin != null) {
			Double res = MomentHelper.computeDifference(fechaIni, fechaFin, ChronoUnit.MONTHS);
			return MathHelper.roundOff(res, 1); //da igual que ponga 88 o 36, siempre redondea a 2 decimales.
		} else
			return 0.0;
	}

	@Mandatory
	//@ValidMoney()
	@Transient
	public Money getCost() {
		Money res = new Money();
		Double dinero = this.inventionRepository.sumCostOfThePartsOfAInventionByInventionId(this.getId());
		if (dinero == null)
			res.setAmount(0.0);
		else
			res.setAmount(dinero);

		res.setCurrency("EUR");

		return res;
	}


	//relaciones
	@Mandatory
	@ManyToOne(optional = false)
	private Inventor inventor;

}
