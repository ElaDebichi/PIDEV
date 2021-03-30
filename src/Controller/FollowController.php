<?php

namespace App\Controller;

use App\Entity\Candidat;
use App\Repository\CandidatRepository;
use App\Repository\EmploiRepository;
use App\Repository\EmployerRepository;
use App\Repository\EvenementsRepository;
use App\Repository\StageRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

class FollowController extends AbstractController
{




    /**
     * @Route("follow/{id}/{iduser}", name="candidatFollow")
     */
    public function followCandidat($id, CandidatRepository $candidatRepository, $iduser, EmployerRepository $UserRepository){

        $candidat = $candidatRepository->find($id);
        $employer= $UserRepository->find($iduser);
        $candidat->setNbrFollow($candidat->getNbrFollow()+1);

        $employer->addCandidat($candidat);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($employer);
        $entityManager->flush();

        return $this->redirectToRoute('candidat_front');
    }

    /**
     * @Route("removeFollow/{id}/{iduser}", name="delete_follow")
     */
    public function removeCandidatFollow($id, CandidatRepository $candidatRepository, $iduser, EmployerRepository $UserRepository){


        $candidat = $candidatRepository->find($id);
        $employer= $UserRepository->find($iduser);
        $candidat->setNbrFollow($candidat->getNbrFollow()-1);

        $employer->removeCandidat($candidat);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();

        return $this->redirectToRoute('candidat_front');
    }


}
