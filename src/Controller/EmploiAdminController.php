<?php

namespace App\Controller;

use App\Entity\Emploi;
use App\Form\EmploiType;
use App\Repository\EmploiRepository;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/jobAdmin")
 */
class EmploiAdminController extends Controller
{
    /**
     * @Route("/", name="emploi_admin_index")
     */
    /*, methods={"GET"}*/
    public function index(Request $request)
    {
        /*$emplois = $this->getDoctrine()->getRepository(Emploi::class)
            ->findAll();*/
        $em = $this->getDoctrine()->getManager();
        $dql = "SELECT e FROM App\Entity\Emploi e";
        $query = $em->createQuery($dql);
        $emplois = $this->get('knp_paginator')->paginate(
            $query,
            $request->query->getInt('page',1)
        );

        return $this->render('emploi_admin/index.html.twig', [
            'emplois' => $emplois,
        ]);
    }

    /**
     * @Route("/new", name="emploi_admin_new", methods={"GET","POST"})
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

            return $this->redirectToRoute('emploi_admin_index');
        }

        return $this->render('emploi_admin/new.html.twig', [
            'emploi' => $emploi,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="emploi_admin_show", methods={"GET"})
     */
    public function show(Emploi $emploi): Response
    {
        return $this->render('emploi_admin/show.html.twig', [
            'emploi' => $emploi,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="emploi_admin_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Emploi $emploi): Response
    {
        $form = $this->createForm(EmploiType::class, $emploi);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('emploi_admin_index');
        }

        return $this->render('emploi_admin/edit.html.twig', [
            'emploi' => $emploi,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="emploi_admin_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Emploi $emploi): Response
    {
        if ($this->isCsrfTokenValid('delete'.$emploi->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($emploi);
            $entityManager->flush();
        }

        return $this->redirectToRoute('emploi_admin_index');
    }

    /**
     * @Route("/update", name="emploi_admin_update")
     */
    public function updateAction(EmploiRepository $emploiRepository): Response
    {
        $emploiRepository->updateDate();
        return $this->redirectToRoute('emploi_admin_index');
    }

}
