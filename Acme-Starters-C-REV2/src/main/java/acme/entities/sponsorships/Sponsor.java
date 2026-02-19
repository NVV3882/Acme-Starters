
package acme.entities.sponsorships;

import javax.persistence.Column;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sponsor extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@Column
	private String				address;

	@Mandatory
	@Column
	private String				im;

	@Mandatory
	@Valid
	@Column
	private Boolean				gold;

}
