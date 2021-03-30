<?php

namespace App\Controller;

use App\Entity\Skills;
use App\Form\SkillsType;
use App\Repository\SkillsRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Candidat;


/**
 * @Route("/ski")
 */
class SkillsFrontController extends AbstractController
{
    /**
     * @Route("/", name="skills_front", methods={"GET"})
     */
    public function index(): Response
    {
        $skillsRepository = $this->getDoctrine()->getRepository(Skills::class);
        return $this->render('skills_front/index.html.twig', [
            'skills' => $skillsRepository->findAll(),
        ]);
    }

    /**
     * @Route("/newSkill/{iduser}/{idskill}", name="skills_new", methods={"GET","POST"})
     */
    public function new(Request $request,$iduser,$idskill): Response
    {

        $candidat = $this->getDoctrine()->getRepository(Candidat::class)->find($iduser);
        $skill = $this->getDoctrine()->getRepository(Skills::class)->find($idskill);
        $candidat->addSkill($skill);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($candidat);
        $entityManager->flush();

            return $this->redirectToRoute('candidat_showFront',  ['id' => $iduser]);


    }
    /**
     * @Route("/deleteSkill/{iduser}/{idskill}", name="skillsFront_delete", methods={"GET","POST"})
     */
    public function deleteSkill(Request $request,$iduser,$idskill): Response
    {

        $candidat = $this->getDoctrine()->getRepository(Candidat::class)->find($iduser);
        $skill = $this->getDoctrine()->getRepository(Skills::class)->find($idskill);
        $candidat->removeSkill($skill);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();

        return $this->redirectToRoute('candidat_showFront',  ['id' => $iduser]);


    }

    /**
     * @Route("/{id}", name="skills_show", methods={"GET"})
     */
    public function show($id): Response
    {
        $skillRepo = $this->getDoctrine()->getRepository(Skills::class);
        $skill = $id !== null ? $skillRepo->find($id) : $skillRepo->findAll();
        return $this->render('skills/show.html.twig', [
            'skill' => $skill,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="skills_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, $id): Response
    {
        $skill = $this->getDoctrine()->getRepository(Skills::class)->find($id);
        $form = $this->createForm(SkillsType::class, $skill);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('skills_index');
        }

        return $this->render('skills/edit.html.twig', [
            'skill' => $skill,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="skills_delete", methods={"DELETE"})
     */
    public function delete(Request $request, $id): Response
    {
        $skill = $this->getDoctrine()->getRepository(Skills::class)->find($id);
        if ($this->isCsrfTokenValid('delete'.$skill->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($skill);
            $entityManager->flush();
        }

        return $this->redirectToRoute('skills_index');
    }
}
