<?php

namespace App\Controller;
use App\Entity\Candidat;
use App\Entity\Employer;
use App\Entity\Formation;
use App\Entity\Urlizer;
use App\Form\FormationType;
use App\Repository\FormationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


/**
 * @Route("/form")
 */
class FormationfrontController extends AbstractController
{
    /**
     * @Route("/", name="formationfront")
     */
    public function index(FormationRepository $formationRepository): Response
    {

        $l = $formationRepository->findAll();

        return $this->render('formationfront/index2.html.twig', [
            'formations' => $l,
        ]);
    }

      /**
     * @Route("/{id}", name="formation_detail", methods={"GET"})
     */
    public function show($id,FormationRepository $formationRepository): Response
    {
        $formation=$formationRepository->find($id);
        return $this->render('formationfront/detail.html.twig', [
            'formation' => $formation,
        ]);
    }


    /**
     * @Route("/new/{iduser}", name="formationFront_new", methods={"GET","POST"})
     */
    public function new(Request $request,$iduser): Response
    {
        $formation = new Formation();
        $employer=$this->getDoctrine()->getRepository(Employer::class)->find($iduser);
        $form = $this->createForm(FormationType::class, $formation);
        $form->handleRequest($request);
        $formation->setNbrparticipants(0);
        $formation->setRating(0);

        if ($form->isSubmitted() && $form->isValid()) {
            $uploadedFile = $form['imageFile']->getData();
            $destination = $this->getParameter('kernel.project_dir').'/public/uploads';
            $originalFilename = pathinfo($uploadedFile->getClientOriginalName(), PATHINFO_FILENAME);
            $newFilename = Urlizer::urlize($originalFilename).'-'.uniqid().'.'.$uploadedFile->guessExtension();
            $uploadedFile->move(
                $destination,
                $newFilename
            );
            $formation->setImg($newFilename);
            $formation->setEmployer($employer);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($formation);
            $entityManager->flush();

            return $this->redirectToRoute('formationfront');
        }

        return $this->render('formationfront/new.html.twig', [
            'formation' => $formation,
            'form' => $form->createView(),
        ]);
    }
    /**
     * @Route("/rating1/{id}/{iduser}/{idcan}", name="rating1", methods={"GET","POST"})
     */
    public function rating1(Request $request,$iduser,$id,$idcan): Response
    {
       $formation=$this->getDoctrine()->getRepository(Formation::class)->find($id);
        $employer=$this->getDoctrine()->getRepository(Employer::class)->find($iduser);
        $candidat =$this->getDoctrine()->getRepository(Candidat::class)->find($idcan);

        $formation->setRating(1);
        $em = $this->getDoctrine()->getManager();

$em->flush($formation);


        return $this->render('employer_front/showFormations.html.twig', [
            'employer' => $employer,
            'candidat' => $candidat,
            ]);
    }
    /**
     * @Route("/rating2/{id}/{iduser}/{idcan}", name="rating2", methods={"GET","POST"})
     */
    public function rating2(Request $request,$iduser,$id,$idcan): Response
    {
        $formation=$this->getDoctrine()->getRepository(Formation::class)->find($id);
        $employer=$this->getDoctrine()->getRepository(Employer::class)->find($iduser);
        $candidat =$this->getDoctrine()->getRepository(Candidat::class)->find($idcan);

        $formation->setRating(2);
        $em = $this->getDoctrine()->getManager();

        $em->flush($formation);


        return $this->render('employer_front/showFormations.html.twig', [
            'employer' => $employer,
            'candidat' => $candidat,
        ]);
    }
    /**
     * @Route("/rating3/{id}/{iduser}/{idcan}", name="rating3", methods={"GET","POST"})
     */
    public function rating3(Request $request,$iduser,$id,$idcan): Response
    {
        $formation=$this->getDoctrine()->getRepository(Formation::class)->find($id);
        $employer=$this->getDoctrine()->getRepository(Employer::class)->find($iduser);
        $candidat =$this->getDoctrine()->getRepository(Candidat::class)->find($idcan);

        $formation->setRating(3);
        $em = $this->getDoctrine()->getManager();

        $em->flush($formation);


        return $this->render('employer_front/showFormations.html.twig', [
            'employer' => $employer,
            'candidat' => $candidat,
        ]);
    }
    /**
     * @Route("/rating4/{id}/{iduser}/{idcan}", name="rating4", methods={"GET","POST"})
     */
    public function rating4(Request $request,$iduser,$id,$idcan): Response
    {
        $formation=$this->getDoctrine()->getRepository(Formation::class)->find($id);
        $employer=$this->getDoctrine()->getRepository(Employer::class)->find($iduser);
        $candidat =$this->getDoctrine()->getRepository(Candidat::class)->find($idcan);

        $formation->setRating(4);
        $em = $this->getDoctrine()->getManager();

        $em->flush($formation);


        return $this->render('employer_front/showFormations.html.twig', [
            'employer' => $employer,
            'candidat' => $candidat,
        ]);
    }
    /**
     * @Route("/rating5/{id}/{iduser}/{idcan}", name="rating5", methods={"GET","POST"})
     */
    public function rating5(Request $request,$iduser,$id,$idcan): Response
    {
        $formation=$this->getDoctrine()->getRepository(Formation::class)->find($id);
        $employer=$this->getDoctrine()->getRepository(Employer::class)->find($iduser);
        $candidat =$this->getDoctrine()->getRepository(Candidat::class)->find($idcan);

        $formation->setRating(5);
        $em = $this->getDoctrine()->getManager();

        $em->flush($formation);


        return $this->render('employer_front/showFormations.html.twig', [
            'employer' => $employer,
            'candidat' => $candidat,
        ]);
    }

    /**
     * @Route("/{id}/edit/{idemp}", name="formationFront_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Formation $formation,$idemp): Response
    {
        $form = $this->createForm(FormationType::class, $formation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $uploadedFile = $form['imageFile']->getData();
            $destination = $this->getParameter('kernel.project_dir').'/public/uploads';
            $originalFilename = pathinfo($uploadedFile->getClientOriginalName(), PATHINFO_FILENAME);
            $newFilename = Urlizer::urlize($originalFilename).'-'.uniqid().'.'.$uploadedFile->guessExtension();
            $uploadedFile->move(
                $destination,
                $newFilename
            );
            $formation->setImg($newFilename);
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('formation_list',['idemp'=>$idemp]);
        }

        return $this->render('formationfront/edit.html.twig', [
            'formation' => $formation,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}/{idemp}", name="formationFront_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Formation $formation,$idemp): Response
    {
        if ($this->isCsrfTokenValid('delete'.$formation->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($formation);
            $entityManager->flush();
        }

        return $this->redirectToRoute('formation_list',['idemp'=>$idemp]);
    }
}