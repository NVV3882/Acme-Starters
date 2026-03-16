/*
 * InventorInventionRepository.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;

@Repository
public interface InventorInventionRepository extends AbstractRepository {

	@Query("select i from Invention i where i.id = :id")
	Invention findInventionById(int id);

	@Query("select i from Invention i where i.inventor.id = :inventorId")
	Collection<Invention> findInventionsByInventorId(int inventorId);

	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> findPartsByInventionId(int inventionId);

	//	
	//	@Query("select sum(d.workLoad) from Duty d where d.job.id = :jobId")
	//	Double computeWorkLoadByJobId(int jobId);
	//
	//	@Query("select c from Company c")
	//	Collection<Company> findAllContractors();
	//
	//	@Query("select c from Company c where c.id = :contractorId")
	//	Company findContractorById(int contractorId);
	//
	//	@Query("select wf.contractor from WorksFor wf where wf.proxy.id = :proxyId")
	//	Collection<Company> findContractorsByProxyId(int proxyId);
	//
	//	@Query("select wf from WorksFor wf where wf.proxy.id = :proxyId and wf.contractor.id = :contractorId")
	//	WorksFor findWorksForByProxyAndContractorId(int proxyId, int contractorId);

}
