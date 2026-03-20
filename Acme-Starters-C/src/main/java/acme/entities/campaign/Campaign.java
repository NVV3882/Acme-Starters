
package acme.entities.campaign;

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
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MathHelper;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidCampaign;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Spokesperson;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidCampaign
public class Campaign extends AbstractEntity {

	/**
	 * 
	 */
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

	// Derivated atributes

	@Transient
	@Autowired
	private CampaignRepository	repository;


	@Transient
	public Double getMonthsActive() {
		Date fechaini = this.startMoment;
		Date fechafin = this.endMoment;

		if (fechaini == null || fechafin == null)
			return 0.0;

		double d = MathHelper.roundOff(MomentHelper.computeDifference(fechaini, fechafin, ChronoUnit.MONTHS), 1);
		return d;
	}

	@Transient
	public double getEffort() {
		double result = 0.0;
		Double suma = this.repository.getEffortById(this.getId());
		if (suma == null)
			return 0.0;
		result += suma;
		return result;
	}

	//Relationships


	@Mandatory
	@ManyToOne(optional = false)
	@Valid
	private Spokesperson spokesperson;

}
