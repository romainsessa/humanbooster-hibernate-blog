package com.hb.blog.repositories;

import java.util.List;

import com.hb.blog.entities.Post;

import jakarta.persistence.EntityManager;

public class PostRepository {

	private EntityManager entityManager;

	public PostRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void create(Post post) {
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(post);
		this.entityManager.getTransaction().commit();
	}

	public Post read(Integer id) {
		return this.entityManager.getReference(Post.class, id);
	}

	public void update(Post post) {
		this.entityManager.getTransaction().begin();
		this.entityManager.merge(post);
		this.entityManager.getTransaction().commit();
	}

	public void delete(Post post) {
		this.entityManager.getTransaction().begin();
		this.entityManager.remove(post);
		this.entityManager.getTransaction().commit();
	}
	
	public List<Post> getPosts() {
		return entityManager.createQuery("from Post").getResultList();
	}

}
