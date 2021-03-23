<?php

namespace App\Controller;

use App\Repository\StageRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class HomeController extends AbstractController
{
    /**
     * @Route("/home", name="home")
     */
    public function index(StageRepository $stageRepository): Response
    {
        return $this->render('test.html.twig', [
            'stages' => $stageRepository->findAll(),
            //'controller_name' => 'HomeController',
        ]);
    }
}
