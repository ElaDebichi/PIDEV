<?php

namespace App\Controller;

use App\Entity\Employer;
use App\Entity\Evenements;
use App\Entity\Urlizer;
use App\Form\EvenementsType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
/**
 * @Route("/event")
 */
class EvenementsFrontController extends AbstractController
{
    /**
     * @Route("/", name="evenements_front")
     */
    public function index(): Response
    {
        return $this->render('evenements_front/index.html.twig', [
            'evenements' => $this->getDoctrine()->getRepository(Evenements::class)->findAll(),
        ]);
    }
    /**
     * @Route("/new/{iduser}", name="evenementsFront_new", methods={"GET","POST"})
     */
    public function newFront(Request $request,$iduser): Response
    {
        $evenement = new Evenements();
        $employer = $this->getDoctrine()->getRepository(Employer::class)->find($iduser);
        $form = $this->createForm(EvenementsType::class, $evenement);
        $form->handleRequest($request);
        $evenement->setNbrParticipants(0);
        $evenement->setEmployer($employer);

        if ($form->isSubmitted() && $form->isValid()) {
            /** @var UploadedFile $uploadedFile */
            $uploadedFile = $form['imageFile']->getData();
            $destination = $this->getParameter('kernel.project_dir').'/public/uploads';
            $originalFilename = pathinfo($uploadedFile->getClientOriginalName(), PATHINFO_FILENAME);
            $newFilename = Urlizer::urlize($originalFilename).'-'.uniqid().'.'.$uploadedFile->guessExtension();
            $uploadedFile->move(
                $destination,
                $newFilename
            );
            $evenement->setImg($newFilename);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($evenement);
            $entityManager->flush();

            return $this->redirectToRoute('evenements_front');
        }

        return $this->render('evenements_front/new.html.twig', [
            'evenement' => $evenement,
            'form' => $form->createView(),
        ]);
    }
    /**
     * @Route("/{id}", name="evenementsEvent_show", methods={"GET"})
     */
    public function show($id): Response
    {
        $evenement = $this->getDoctrine()->getRepository(Evenements::class)->find($id);
        return $this->render('evenements_front/show.html.twig', [
            'event' => $evenement,
        ]);
    }
    /**
     * @Route("/{id}/edit/{idemp}", name="eventFront_edit", methods={"GET","POST"})
     */
    public function editFront(Request $request, Evenements $evenement,$idemp): Response
    {
        $form = $this->createForm(EvenementsType::class, $evenement);
        $form->handleRequest($request);
        $employer = $this->getDoctrine()->getRepository(Employer::class)->find($idemp);
        if ($form->isSubmitted() && $form->isValid()) {
            /** @var UploadedFile $uploadedFile */
            $uploadedFile = $form['imageFile']->getData();
            $destination = $this->getParameter('kernel.project_dir') . '/public/uploads';
            $originalFilename = pathinfo($uploadedFile->getClientOriginalName(), PATHINFO_FILENAME);
            $newFilename = Urlizer::urlize($originalFilename) . '-' . uniqid() . '.' . $uploadedFile->guessExtension();
            $uploadedFile->move(
                $destination,
                $newFilename
            );
            $evenement->setImg($newFilename);
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('events_list', ['idemp' => $idemp]);
        }
        return $this->render('evenements_front/edit.html.twig', [
            'evenement' => $evenement,
            'form' => $form->createView(),
        ]);
    }
    /**
     * @Route("/{id}/{idemp}", name="evenementsFront_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Evenements $evenement,$idemp): Response
    {
        $employer = $this->getDoctrine()->getRepository(Employer::class)->find($idemp);
        if ($this->isCsrfTokenValid('delete'.$evenement->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($evenement);
            $entityManager->flush();
        }

        return $this->redirectToRoute('events_list', ['idemp' => $idemp]);
    }

}
