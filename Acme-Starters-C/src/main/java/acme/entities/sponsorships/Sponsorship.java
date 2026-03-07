
package acme.entities.sponsorships;

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
import acme.constraints.ValidHeader;
import acme.constraints.ValidSponsorship;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidSponsorship
public class Sponsorship extends AbstractEntity {

	private static final long		serialVersionUID	= 1L;

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String					ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String					name;

	@Mandatory
	@ValidText
	@Column
	private String					description;

	@Mandatory
	@ValidMoment()
	@Temporal(TemporalType.TIMESTAMP)
	private Date					startMoment;

	@Mandatory
	@ValidMoment()
	@Temporal(TemporalType.TIMESTAMP)
	private Date					endMoment;

	@Optional
	@ValidUrl
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


	@Transient
	public Double monthsActive() {
		Date fechaini = this.startMoment;
		Date fechafin = this.endMoment;
		if (fechaini == null && fechafin == null)
			return null;
		return 0.0;

	}

	@Transient
	public Money totalMoney() {
		Money result = new Money();
		Double suma = this.repository.sumDonationsOfSponsorship(this.getId());
		if (suma == null)
			suma = 0.0;
		result.setAmount(suma);
		result.setCurrency("EUR");
		return result;
	}


	//Relationships
	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Sponsor sponsor;

}
