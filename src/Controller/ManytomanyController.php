<?php

namespace App\Controller;

use App\Entity\Candidat;
use App\Entity\Skills;
use App\Entity\Urlizer;
use App\Entity\User;
use App\Form\CandidatType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
/**
 * @Route("/candidat")
 */
class ManytomanyController extends AbstractController
{
    /**
     * @Route("new/{id}", name="candidat_newFront", methods={"GET","POST"})
     */
    public function new($id): Response
    {
        $candidat = $this->getDoctrine()->getRepository(Candidat::class)->find($id);
        $skill = $this->getDoctrine()->getRepository(Skills::class)->find($id);
        $candidat->addSkill($skill);


        return $this->render('candidat_front/show.html.twig', [
            'candidat' => $candidat,

        ]);
    }
}