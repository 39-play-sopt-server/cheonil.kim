package org.sopt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Post> posts = new ArrayList<>();
        PostController postController = new PostController();
        postController.run();
    }
}