<?php

namespace App\Controller;

use App\Entity\Candidat;
use App\Repository\CandidatRepository;
use App\Repository\EmploiRepository;
use App\Repository\EvenementsRepository;
use App\Repository\FormationRepository;
use App\Repository\StageRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

class ParticiperController extends AbstractController
{




    /**
     * @Route("participerFormation/{id}/{iduser}", name="formation_participer")
     */
    public function participerFormation($id, FormationRepository $formRepository, $iduser, CandidatRepository $UserRepository){

        $formation = $formRepository->find($id);
        $candidat= $UserRepository->find($iduser);
        $formation->setNbrParticipants($formation->getNbrParticipants()+1);

        $formation->addParticiper($candidat);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($candidat);
        $entityManager->flush();

        return $this->redirectToRoute('formation_detail',['id' =>$id,]);
    }

    /**
     * @Route("removeParticiperForm/{id}/{idemp}/{iduser}", name="delete_participerFromation")
     */
    public function removeFromationParticiper($id, FormationRepository $formRepository, $iduser, CandidatRepository $UserRepository,$idemp){

        $formation = $formRepository->find($id);
        $candidat= $UserRepository->find($iduser);
        $formation->setNbrParticipants($formation->getNbrParticipants()-1);

        $formation->removeParticiper($candidat);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();

        return $this->redirectToRoute('formation_detail',['id' =>$id,]);
    }


}
