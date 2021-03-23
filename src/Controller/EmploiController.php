<?php

namespace App\Controller;

use App\Entity\Categorie;
use App\Entity\Emploi;
use App\Entity\Search;
use App\Form\EmploiType;
use App\Form\SearchType;
use App\Repository\EmploiRepository;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/emploi")
 */
class EmploiController extends AbstractController
{
    public function indexDefault(EmploiRepository $emploiRepository): Response
    {
        return $this->render('emploi/index.html.twig', [
            'emplois' => $emploiRepository->findAll(),
        ]);
    }

    /**
     * @Route("/", name="emploi_index", methods={"GET", "POST"})
     */
    public function finds(Request $request)
    {
        $categorySearch = new Search();
        $form = $this->createForm(SearchType::class,$categorySearch);
        $form->handleRequest($request);

        $emplois= $this->getDoctrine()->getRepository(Emploi::class)->findAll();

        if($form->isSubmitted() && $form->isValid()) {
            $category = $categorySearch->getCategory();

            if ($category!="")
            {
                $emplois= $this->getDoctrine()->getRepository(Emploi::class)->findBy(['category' => $category] );
            }
            else
                $emplois= $this->getDoctrine()->getRepository(Emploi::class)->findAll();
        }

        return $this->render('emploi/index.html.twig',['form' => $form->createView(),'emplois' => $emplois]);
    }

    /**
     * @Route("/C", name="emploi_indexC", methods={"GET", "POST"})
     */
    public function findsC(Request $request)
    {
        $categorySearch = new Search();
        $form = $this->createForm(SearchType::class,$categorySearch);
        $form->handleRequest($request);

        $emplois= $this->getDoctrine()->getRepository(Emploi::class)->findAll();

        if($form->isSubmitted() && $form->isValid()) {
            $category = $categorySearch->getCategory();

            if ($category!="")
            {
                $emplois= $this->getDoctrine()->getRepository(Emploi::class)->findBy(['category' => $category] );
            }
            else
                $emplois= $this->getDoctrine()->getRepository(Emploi::class)->findAll();
        }

        return $this->render('emploi/indexC.html.twig',['form' => $form->createView(),'emplois' => $emplois]);
    }

    /**
     * @Route("/new", name="emploi_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $emploi = new Emploi();
        $form = $this->createForm(EmploiType::class, $emploi);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($emploi);
            $entityManager->flush();

            return $this->redirectToRoute('emploi_index');
        }

        return $this->render('emploi/new.html.twig', [
            'emploi' => $emploi,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="emploi_show", methods={"GET"})
     */
    public function show(Emploi $emploi): Response
    {
        return $this->render('emploi/show.html.twig', [
            'emploi' => $emploi,
        ]);
    }

    /**
     * @Route("/{id}/C", name="emploi_showC", methods={"GET"})
     */
    public function showC(Emploi $emploi): Response
    {
        return $this->render('emploi/showC.html.twig', [
            'emploi' => $emploi,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="emploi_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Emploi $emploi): Response
    {
        $form = $this->createForm(EmploiType::class, $emploi);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('emploi_index');
        }

        return $this->render('emploi/edit.html.twig', [
            'emploi' => $emploi,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="emploi_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Emploi $emploi): Response
    {
        if ($this->isCsrfTokenValid('delete'.$emploi->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($emploi);
            $entityManager->flush();
        }

        return $this->redirectToRoute('emploi_index');
    }



}
