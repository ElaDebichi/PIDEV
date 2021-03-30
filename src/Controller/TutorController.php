<?php

namespace App\Controller;

use App\Entity\Employer;
use App\Entity\Tutor;
use App\Form\TutorType;
use App\Repository\TutorRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/tutor")
 */
class TutorController extends AbstractController
{
    /**
     * @Route("/", name="tutor_index", methods={"GET"})
     */
    public function index(TutorRepository $tutorRepository): Response
    {
        return $this->render('tutor/index.html.twig', [
            'tutors' => $tutorRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new/{iduser}", name="tutor_new", methods={"GET","POST"})
     */
    public function new(Request $request,$iduser): Response
    {
        $tutor = new Tutor();
        $form = $this->createForm(TutorType::class, $tutor);
        $form->handleRequest($request);
        $employer = $this->getDoctrine()->getRepository(Employer::class)->find($iduser);
        $tutor->setEmployer($employer);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($tutor);
            $entityManager->flush();

            return $this->redirectToRoute('tutors_list',['idemp'=>$iduser]);
        }

        return $this->render('tutor/new.html.twig', [
            'tutor' => $tutor,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="tutor_show", methods={"GET"})
     */
    public function show(Tutor $tutor): Response
    {
        return $this->render('tutor/show.html.twig', [
            'tutor' => $tutor,
        ]);
    }

    /**
     * @Route("/{id}/edit/{iduser}", name="tutor_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Tutor $tutor,$iduser): Response
    {
        $form = $this->createForm(TutorType::class, $tutor);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('tutors_list',['idemp'=>$iduser]);
        }

        return $this->render('tutor/edit.html.twig', [
            'tutor' => $tutor,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}/{iduser}", name="tutor_delete", methods={"POST"})
     */
    public function delete(Request $request, Tutor $tutor,$iduser): Response
    {
        if ($this->isCsrfTokenValid('delete'.$tutor->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($tutor);
            $entityManager->flush();
        }

        return $this->redirectToRoute('tutors_list',['idemp'=>$iduser]);
    }
}
