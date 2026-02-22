
package acme.realms;

import javax.persistence.Column;
import javax.persistence.Entity;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Inventor extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@Column
	private String				bio;

	@Mandatory
	@Column
	private String				keyWords;

	@Mandatory
	@Column
	private Boolean				licensed;

}
