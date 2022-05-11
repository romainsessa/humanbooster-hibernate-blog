package com.hb.blog.repositories;

import java.util.List;

import com.hb.blog.entities.Tag;

import jakarta.persistence.EntityManager;

public class TagRepository {

	private EntityManager entityManager;

	public TagRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void create(Tag tag) {
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(tag);
		this.entityManager.getTransaction().commit();
	}

	public Tag read(Integer id) {
		return this.entityManager.getReference(Tag.class, id);
	}

	public void update(Tag tag) {
		this.entityManager.getTransaction().begin();
		this.entityManager.merge(tag);
		this.entityManager.getTransaction().commit();
	}

	public void delete(Tag tag) {
		this.entityManager.getTransaction().begin();
		this.entityManager.remove(tag);
		this.entityManager.getTransaction().commit();
	}

	public List<Tag> getTags() {		
		return entityManager.createQuery("from Tag").getResultList();
	}

}
