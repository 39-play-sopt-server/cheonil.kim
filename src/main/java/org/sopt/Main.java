package org.sopt;

import org.sopt.client.PostClient;
import org.sopt.controller.PostController;
import org.sopt.repository.InMemoryPostRepository;
import org.sopt.repository.PostRepository;
import org.sopt.service.PostService;
import org.sopt.view.InputView;
import org.sopt.view.OutputView;

public class Main {

    public static void main(String[] args) {
        PostRepository repository = new InMemoryPostRepository();
        PostService service = new PostService(repository);
        PostController controller = new PostController(service);

        PostClient client = new PostClient(controller, new InputView(), new OutputView());
        client.run();
    }
}
