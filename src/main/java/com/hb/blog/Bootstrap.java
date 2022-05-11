package com.hb.blog;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hb.blog.entities.Post;
import com.hb.blog.entities.PostComment;
import com.hb.blog.entities.PostDetails;
import com.hb.blog.entities.Tag;
import com.hb.blog.repositories.PostCommentRepository;
import com.hb.blog.repositories.PostRepository;
import com.hb.blog.repositories.TagRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Bootstrap {

	private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

	public static void main(String[] args) {

		boolean continueApp = true;
		Scanner sc = new Scanner(System.in);
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("com.hb.blog");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		while (continueApp) {

			boolean askEntity = true;
			int entityChoice = 0;			
			do {
				logger.info("Quelle entité souhaitez-vous utiliser ? \n 1. Post \n 2. Comment \n 3. Tag");
				entityChoice = Integer.parseInt(sc.nextLine());
				if (entityChoice == 1 || entityChoice == 2 || entityChoice == 3) {
					askEntity = false;
				} else {
					logger.warn("Attention, vous devez choisir une entité entre 1, 2 et 3.");
				}
			} while (askEntity);

			boolean askAction = true;
			int actionChoice = 0;
			do {
				String txt = "Quelle action souhaitez-vous effectuer ? \n 1. Creer \n 2. Lire \n 3. Mettre à jour \n 4. Supprimer";
				if (entityChoice == 1 || entityChoice == 3) {
					txt += "\n 5. Associer un post à un tag";
				}
				logger.info(txt);
				actionChoice = Integer.parseInt(sc.nextLine());

				if (actionChoice == 1 || actionChoice == 2 || actionChoice == 3 || actionChoice == 4
						|| actionChoice == 5) {
					askAction = false;
				} else {
					logger.warn("Attention, vous devez choisir un numéro d'action entre 1, 2, 3, 4 et 5");
				}
			} while (askAction);

			PostRepository pRepo = new PostRepository(entityManager);
			PostCommentRepository cRepo = new PostCommentRepository(entityManager);
			TagRepository tRepo = new TagRepository(entityManager);

			switch (actionChoice) {
			case 1:
				if (entityChoice == 1) {
					logger.info("Quel est le titre du post ?");
					String title = sc.nextLine();
					Post p = new Post();
					p.setTitle(title);
					logger.info("Qui a créé le post ?");
					String createdBy = sc.nextLine();
					PostDetails detail = new PostDetails();
					detail.setCreated_by(createdBy);
					detail.setCreate_on(new Date());
					p.setDetails(detail);
					pRepo.create(p);
				} else if (entityChoice == 2) {
					logger.info("Quel est le contenu du commentaire ?");
					String review = sc.nextLine();
					logger.info("Quel est l'id du post associé ? ");
					int postId = Integer.parseInt(sc.nextLine());
					PostComment c = new PostComment();
					c.setReview(review);
					Post p = pRepo.read(postId);
					c.setPost(p);
					cRepo.create(c);
				} else if (entityChoice == 3) {
					logger.info("Quel est le nom du tag ?");
					String name = sc.nextLine();
					Tag t = new Tag();
					t.setName(name);
					tRepo.create(t);
				}
				break;
			case 2:
				if (entityChoice == 1) {
					List<Post> posts = pRepo.getPosts();
					for (Post p : posts) {
						logger.info(p.toString());
					}
				} else if (entityChoice == 2) {
					List<PostComment> comments = cRepo.getComments();
					for (PostComment c : comments) {
						logger.info(c.toString());
					}
				} else if (entityChoice == 3) {
					List<Tag> tags = tRepo.getTags();
					for (Tag t : tags) {
						logger.info(t.toString());
					}
				}
				break;
			case 3:
				if (entityChoice == 1) {
					logger.info("Quel est l'id du post à modifier ?");
					int postId = Integer.parseInt(sc.nextLine());
					Post p = pRepo.read(postId);
					logger.info(
							"Quel est le nouveau titre du post ? (Appuyez juste sur entrée pour garder le même titre)");
					String title = sc.nextLine();
					if (!title.equals("")) {
						p.setTitle(title);
					}
					logger.info("Quel est le nouvel auteur ? (Appuyez juste sur entrée pour garder le même titre)");
					String createdBy = sc.nextLine();
					if (!createdBy.equals("")) {
						p.getDetails().setCreated_by(createdBy);
					}
					logger.info("La date de création ne peut pas être mise à jour");
					pRepo.update(p);
				} else if (entityChoice == 2) {
					logger.info("Quel est l'id du commentaire à modifier ?");
					int commentId = Integer.parseInt(sc.nextLine());
					logger.info("Quel est le nouveau contenu du commentaire ?");
					String review = sc.nextLine();
					PostComment c = cRepo.read(commentId);
					c.setReview(review);
					cRepo.update(c);
				} else if (entityChoice == 3) {
					logger.info("Quel est l'id du tag à modifier ?");
					int tagId = Integer.parseInt(sc.nextLine());
					logger.info("Quel est le nouveau nom du tag ?");
					String name = sc.nextLine();
					Tag t = tRepo.read(tagId);
					t.setName(name);
					tRepo.update(t);
				}
				break;
			case 4:
				logger.info("Quel est l'id de l'entité à supprimer ? ");
				int id = Integer.parseInt(sc.nextLine());
				if (entityChoice == 1) {
					Post p = pRepo.read(id);
					pRepo.delete(p);
				} else if (entityChoice == 2) {
					PostComment c = cRepo.read(id);
					cRepo.delete(c);
				} else if (entityChoice == 3) {
					Tag t = tRepo.read(id);
					tRepo.delete(t);
				}
				break;
			case 5:
				if (entityChoice == 1 || entityChoice == 3) {
					logger.info("Quel est l'id du post ?");
					int postId = Integer.parseInt(sc.nextLine());
					Post p = pRepo.read(postId);

					logger.info("Quel est l'id du tag ?");
					int tagId = Integer.parseInt(sc.nextLine());
					Tag t = tRepo.read(tagId);

					p.addTag(t);
					pRepo.update(p);
				} else if (entityChoice == 2) {
					logger.warn("Attention, le numéro d'action 5 n'est pas valide pour l'entité Comment.");
				}
				break;
			}

			logger.info(
					"Voulez-vous continuer à utiliser l'app ? Si oui tapez 1, sinon tappez n'importe quel chiffre.");
			int rep = Integer.parseInt(sc.nextLine());
			if (rep != 1) {
				continueApp = false;
			}

		}

		entityManager.close();
		entityManagerFactory.close();
		sc.close();
	}

}