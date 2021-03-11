<?php

namespace App\Controller;

use App\Entity\Post;
use App\Form\Post1Type;
use App\Repository\PostRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
class UserPostController extends AbstractController
{
    /**
     * @Route("/user/post", name="user_post", methods={"GET"})
     */
    public function index(PostRepository $postRepository): Response
    {
        return $this->render('user_post/index.html.twig', [
            'posts' => $postRepository->findAll(),
        ]);
    }

    /**
     * @Route("/user/post/new", name="user_post_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $post = new Post();
        $form = $this->createForm(Post1Type::class, $post);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $post->setNblikes(0);
            $post->setDate(new \DateTimeImmutable('now'));
            $entityManager->persist($post);
            $entityManager->flush();

            return $this->redirectToRoute('user_post');
        }

        return $this->render('user_post/new.html.twig', [
            'post' => $post,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("user/post/{id}/edit", name="user_post_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Post $post): Response
    {
        $form = $this->createForm(Post1Type::class, $post);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('user_post');
        }

        return $this->render('user_post/edit.html.twig', [
            'post' => $post,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("user/post/{id}", name="user_post_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Post $post): Response
    {
        if ($this->isCsrfTokenValid('delete'.$post->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($post);
            $entityManager->flush();
        }

        return $this->redirectToRoute('user_post');
    }







}
