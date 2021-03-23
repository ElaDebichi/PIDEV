<?php

namespace App\Controller;

use App\Entity\Emploi;
use App\Form\EmploiType;
use App\Repository\EmploiRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/emploiAdmin")
 */
class EmploiAdminController extends AbstractController
{
    /**
     * @Route("/", name="emploi_admin_index", methods={"GET"})
     */
    public function index(EmploiRepository $emploiRepository): Response
    {
        $emplois = $this->getDoctrine()->getRepository(Emploi::class)
            ->findAll();
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
        //$emplois = $emploiRepository->updateDate();
        $emploiRepository->updateDate();
        /*return $this->render('emploi_admin/index.html.twig', array(
            'emplois' => $emplois));*/
        return $this->redirectToRoute('emploi_admin_index');
    }

    /**
     * @Route("/triAscLib", name="emploi_triAsc_libelle")
     */
    public function triAscLib(EmploiRepository $emploiRepository)
    {
        $emplois = $emploiRepository->triAscLib();

        return $this->render('emploi_admin/index.html.twig', array(
            'emplois' => $emplois));
    }

    /**
     * @Route("/triDescLib", name="emploi_triDesc_libelle")
     */
    public function triDescLib(EmploiRepository $emploiRepository): Response
    {
        $emplois = $emploiRepository->triDescLib();

        return $this->render('emploi_admin/index.html.twig', array(
            'emplois' => $emplois));
    }

    /**
     * @Route("/triAscId", name="emploi_triAsc_id")
     */
    public function triAscId(EmploiRepository $emploiRepository): Response
    {
        $emplois = $emploiRepository->triAscId();

        return $this->render('emploi_admin/index.html.twig', array(
            'emplois' => $emplois));
    }
    /**
     * @Route("/triDescId", name="emploi_triDesc_id")
     */
    public function triDescId(EmploiRepository $emploiRepository): Response
    {
        $emplois = $emploiRepository->triDescId();

        return $this->render('emploi_admin/index.html.twig', array(
            'emplois' => $emplois));
    }

}
