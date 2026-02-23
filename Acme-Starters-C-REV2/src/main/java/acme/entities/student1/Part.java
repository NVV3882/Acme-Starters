
package acme.entities.student1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Part extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@Column
	private String				name;

	@Mandatory
	@Column
	private String				description;

	@Mandatory
	@Column
	private Money				cost;

	@Mandatory
	@Column
	private PartKind			kind;

	@Mandatory
	@ManyToOne
	private Invention			invention;

}
