<?php

namespace App\Controller;

use App\Entity\Comment;
use App\Entity\Post;
use App\Form\CommentType;
use App\Form\Post1Type;
use App\Repository\PostRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/c")
 */
class HomeController extends AbstractController
{
    /**
     * @Route("/", name="home_visitor")
     */
    public function index1()
    {
        return $this->render('homevisitor.html.twig', [

        ]);
    }

    /**
     * @Route("/user", name="home_user")
     */
    public function index2()
    {
        return $this->render('home.html.twig', [

        ]);
    }

    /**
     * @Route("/notauthorized", name="not_authorized")
     */
    public function index3()
    {
        return $this->render('notauthorized.html.twig', [

        ]);
    }









}