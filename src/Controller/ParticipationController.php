<?php

namespace App\Controller;

use App\Entity\Candidat;
use App\Repository\CandidatRepository;
use App\Repository\EmploiRepository;
use App\Repository\EvenementsRepository;
use App\Repository\StageRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

class ParticipationController extends AbstractController
{




    /**
     * @Route("participer/{id}/{iduser}", name="event_participer")
     */
    public function participerEvent($id, EvenementsRepository  $eventRepository, $iduser, CandidatRepository $UserRepository){

        $event = $eventRepository->find($id);
        $candidat= $UserRepository->find($iduser);
        $event->setNbrParticipants($event->getNbrParticipants()+1);

        $event->addCandidat($candidat);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($event);
        $entityManager->flush();

        return $this->redirectToRoute('evenementsEvent_show',['id' =>$id,]);
    }

    /**
     * @Route("removeParticiper/{id}/{idemp}/{iduser}", name="delete_participer")
     */
    public function removeEventParticiper($id, EvenementsRepository  $eventRepository, $iduser, CandidatRepository $UserRepository,$idemp){

        $event = $eventRepository->find($id);
        $candidat= $UserRepository->find($iduser);
        $event->setNbrParticipants($event->getNbrParticipants()-1);

        $event->removeCandidat($candidat);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();

        return $this->redirectToRoute('employer_showFront',['idemp' =>$idemp,]);
    }


}
