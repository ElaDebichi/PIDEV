<?php

namespace App\Controller;

use App\Entity\Employer;
use App\Entity\Evenements;
use App\Entity\Urlizer;
use App\Form\EvenementsType;
use App\Repository\EvenementsRepository;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;


/**
 * @Route("/evenements")
 */
class EvenementsController extends AbstractController
{
    /**
     * @Route("/", name="evenements_index", methods={"GET"})
     */
    public function index(): Response
    {

        return $this->render('evenements/index.html.twig', [
            'evenements' => $this->getDoctrine()->getRepository(Evenements::class)->findAll(),
        ]);
    }
    /**
     * @Route("/event", name="evenements_indexFront", methods={"GET"})
     */

    public function index_front(EvenementsRepository $evenementsRepository): Response
    {
        return $this->render('evenements/indexFront.html.twig', [
            'evenements' => $evenementsRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new/{iduser}", name="evenements_new", methods={"GET","POST"})
     */
    public function new(Request $request,$iduser): Response
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

            return $this->redirectToRoute('evenements_index');
        }

        return $this->render('evenements/new.html.twig', [
            'evenement' => $evenement,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="evenements_show", methods={"GET"})
     */
    public function show(Evenements $evenement): Response
    {
        return $this->render('evenements/show.html.twig', [
            'evenement' => $evenement,
        ]);
    }
    /**
     * @Route("/front/{id}", name="evenements_showFront", methods={"GET"})
     */
    public function showfront(Evenements $evenement): Response
    {
        return $this->render('evenements/showFront.html.twig', [
            'evenement' => $evenement,
        ]);
    }


    /**
     * @Route("/{id}/edit", name="evenements_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Evenements $evenement): Response
    {
        $form = $this->createForm(EvenementsType::class, $evenement);
        $form->handleRequest($request);

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
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('evenements_index');
        }

        return $this->render('evenements/edit.html.twig', [
            'evenement' => $evenement,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="evenements_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Evenements $evenement): Response
    {
        if ($this->isCsrfTokenValid('delete'.$evenement->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($evenement);
            $entityManager->flush();
        }

        return $this->redirectToRoute('evenements_index');
    }


    /**
     * @Route("/searchStudentxxx ",name="searchStudentxxx")
     * @param Request $request
     * @param NormalizerInterface $Normalizer
     * @return Response
     * @throws ExceptionInterface
     */
public function searchStudentx(Request $request,NormalizerInterface $Normalizer)

    {

        $requestString=$request->get('searchValue');
        $events= $this->getDoctrine()->getRepository(Evenements::class)->findEventByTitle($requestString);
        $jsonContent = $Normalizer->normalize($events, 'json',['groups'=>'events']);
        $retour=json_encode($jsonContent);
        return new JsonResponse($jsonContent);

    }


}
