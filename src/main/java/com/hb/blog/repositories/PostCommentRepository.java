package com.hb.blog.repositories;

import java.util.List;

import com.hb.blog.entities.PostComment;

import jakarta.persistence.EntityManager;

public class PostCommentRepository {

	private EntityManager entityManager;

	public PostCommentRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void create(PostComment comment) {
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(comment);
		this.entityManager.getTransaction().commit();
	}

	public PostComment read(Integer id) {
		return this.entityManager.getReference(PostComment.class, id);
	}

	public void update(PostComment comment) {
		this.entityManager.getTransaction().begin();
		this.entityManager.merge(comment);
		this.entityManager.getTransaction().commit();
	}

	public void delete(PostComment comment) {
		this.entityManager.getTransaction().begin();
		this.entityManager.remove(comment);
		this.entityManager.getTransaction().commit();
	}

	public List<PostComment> getComments() {
		return entityManager.createQuery("from PostComment").getResultList();
	}

}
