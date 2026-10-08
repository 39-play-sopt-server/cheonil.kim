package org.sopt;

import org.sopt.controller.PostController;
import org.sopt.repository.InMemoryPostRepository;
import org.sopt.repository.PostRepository;
import org.sopt.service.PostService;
import org.sopt.view.PostView;

public class Main {

    public static void main(String[] args) {
        PostRepository repository = new InMemoryPostRepository();
        PostService service = new PostService(repository);
        PostController controller = new PostController(service, new PostView());
        controller.run();
    }
}