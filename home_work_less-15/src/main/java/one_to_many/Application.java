package one_to_many;

import java.nio.channels.SeekableByteChannel;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Application {

	public static void main(String[] args) {
		
		Configuration configuration = new Configuration();
		configuration.configure("/META-INF/hibernate.cfg.xml");
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		Session session = configuration.buildSessionFactory(serviceRegistry).openSession();
		
		Post post = new Post();
		post.setTitle("myPost");
		
		Comment comment1 = new Comment();
		comment1.setAuthorName("Vasyl");
		comment1.setPost(post);
		
		Comment comment2 = new Comment();
		comment2.setAuthorName("Petro");
		comment2.setPost(post);
		
		Set<Comment> comments = new HashSet<>();
		comments.add(comment1);
		comments.add(comment2);
		
		post.setComments(comments);
		
		Transaction transaction = session.beginTransaction();
		session.save(post);
		transaction.commit();
		
		Post postDB = (Post) session.get(Post.class, 1);
		System.out.println(postDB+"------->"+postDB.getComments());
		
		Comment commentDB = (Comment) session.get(Comment.class, 1);
		System.out.println(commentDB+"----->"+commentDB.getPost());
	}
}
