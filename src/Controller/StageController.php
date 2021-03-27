<?php

namespace App\Controller;

use App\Entity\Employer;
use App\Entity\Search;
use App\Entity\Stage;
use App\Entity\User;
use App\Form\SearchType;
use App\Form\StageType;
use App\Repository\StageRepository;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


/**
 * @Route("/internship")
 */
class StageController extends Controller
{
    public function indexDefault(StageRepository $stageRepository, Request $request): Response
    {
        $allstages = $stageRepository->findAll();
        $stages = $this->get('knp_paginator')->paginate(
        // Doctrine Query, not results
            $allstages,
            // Define the page parameter
            $request->query->getInt('page', 1),
            // Items per page
            2
        );
        return $this->render('stage/index.html.twig', [
            'stages' => $stages,
        ]);
    }

    /**
     * @Route("/", name="stage_index", methods={"GET", "POST"})
     */
    public function finds(Request $request, StageRepository $stageRepository)
    {
        $categorySearch = new Search();
        $form = $this->createForm(SearchType::class,$categorySearch);
        $form->handleRequest($request);

        $allstages= $this->getDoctrine()->getRepository(Stage::class)->findAll();

        if($form->isSubmitted() && $form->isValid()) {
            $category = $categorySearch->getCategory();

            if ($category!="")
            {
                $allstages= $this->getDoctrine()->getRepository(Stage::class)->findBy(['category' => $category] );
            }
            else
                $allstages= $this->getDoctrine()->getRepository(Stage::class)->findAll();
        }
        $stages = $this->get('knp_paginator')->paginate(
            $allstages,
            $request->query->getInt('page', 1),
            4
        );
        return $this->render('stage/index.html.twig',['form' => $form->createView(),'stages' => $stages]);
    }

    /**
     * @Route("/C", name="stage_indexC", methods={"GET", "POST"})
     */
    public function findsC(Request $request)
    {
        $categorySearch = new Search();
        $form = $this->createForm(SearchType::class,$categorySearch);
        $form->handleRequest($request);

        $allstages= $this->getDoctrine()->getRepository(Stage::class)->findAll();

        if($form->isSubmitted() && $form->isValid()) {
            $category = $categorySearch->getCategory();

            if ($category!="")
            {
                $allstages= $this->getDoctrine()->getRepository(Stage::class)->findBy(['category' => $category] );
            }
            else
                $allstages= $this->getDoctrine()->getRepository(Stage::class)->findAll();
        }
        $stages = $this->get('knp_paginator')->paginate(
            $allstages,
            $request->query->getInt('page', 1),
            4
        );

        return $this->render('stage/indexC.html.twig',['form' => $form->createView(),'stages' => $stages]);
    }

    /**
     * @Route("/new/{iduser}", name="stage_new", methods={"GET","POST"})
     */
    public function new(Request $request,$iduser): Response
    {
        $stage = new Stage();
        $employer = $this->getDoctrine()->getRepository(User::class)->find($iduser);
        $form = $this->createForm(StageType::class, $stage);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $stage->setUser($employer);
            $entityManager->persist($stage);
            $entityManager->flush();

            return $this->redirectToRoute('stage_index');
        }

        return $this->render('stage/new.html.twig', [
            'stage' => $stage,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="stage_show", methods={"GET"})
     */
    public function show(Stage $stage): Response
    {
        return $this->render('stage/show.html.twig', [
            'stage' => $stage,
        ]);
    }

    /**
     * @Route("/{id}/C", name="stage_showC", methods={"GET"})
     */
    public function showC(Stage $stage): Response
    {
        return $this->render('stage/showC.html.twig', [
            'stage' => $stage,
        ]);
    }

    /**
     * @Route("/{id}/edit/{idemp}", name="stage_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Stage $stage,$idemp): Response
    {
        $form = $this->createForm(StageType::class, $stage);
        $form->handleRequest($request);
        $employer = $this->getDoctrine()->getRepository(Employer::class)->find($idemp);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('employer_showFront', ['idemp' => $idemp]);
        }

        return $this->render('stage/edit.html.twig', [
            'stage' => $stage,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}/{idemp}", name="stage_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Stage $stage,$idemp): Response
    {
        $employer = $this->getDoctrine()->getRepository(Employer::class)->find($idemp);
        if ($this->isCsrfTokenValid('delete'.$stage->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($stage);
            $entityManager->flush();
        }

        return $this->redirectToRoute('employer_showFront', ['idemp' => $idemp]);
    }
    


}
