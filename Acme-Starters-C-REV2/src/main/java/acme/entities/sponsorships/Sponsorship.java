
package acme.entities.sponsorships;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	private static final long		serialVersionUID	= 1L;

	@Mandatory
	//@ValidTicker
	@Column(unique = true)
	private String					ticker;

	@Mandatory
	//@ValidHeader
	@Column
	private String					title;

	@Mandatory
	//@ValidText
	@Column
	private String					description;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					startMoment;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					endMoment;

	@Optional
	//@ValidUrl
	@Column
	private String					moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean					draftMode;

	//Derived attributes
	@Transient
	@Autowired
	private SponsorshipRepository	repository;

	//	@Transient
	//	public Double monthsActive() {
	//		Date fecha = this.startMoment;
	//
	//	}


	@Transient
	public Money totalMoney() {
		Money result = null;
		for (Donation don : this.donations)
			if (result == null)
				result = don.getMoney();
			else {
				Double suma = result.getAmount() + don.getMoney().getAmount();
				result.setAmount(suma);
			}

		return result;
	}


	//Relationships
	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Sponsor			sponsor;

	@Mandatory
	@Valid
	@OneToMany()
	private List<Donation>	donations;
}
