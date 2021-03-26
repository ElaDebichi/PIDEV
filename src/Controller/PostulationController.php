<?php

namespace App\Controller;

use App\Entity\Candidat;
use App\Entity\Employer;
use App\Repository\CandidatRepository;
use App\Repository\EmploiRepository;
use App\Repository\StageRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

class PostulationController extends AbstractController
{
    /**
     * @Route("/internshipApply", name="postulation_stage")
     */
    public function index(SessionInterface $session, StageRepository $stageRepository)
    {
        $stage= $session->get('stage', []);
        $stageWithData = [];

        foreach($stage as $id){
            $stageWithData[] = [
                'stage' => $stageRepository->find($id),
            ];
        }
        //dd($stageWithData);

        return $this->render('postulation/StageApply.html.twig', [
            'items' => $stageWithData
        ]);
    }

    /**
     * @Route("/jobApply/{iduser}/{idemployer}", name="postulation_emploi")
     */
    public function indexEmploi(SessionInterface $session, EmploiRepository $emploiRepository, $iduser,$idemployer)
    {
        $emploi= $session->get('emploi',[]);
        $candidat = $this->getDoctrine()->getRepository(Candidat::class)->find($iduser);
        $emploiWithData = [];
        $employer= $this->getDoctrine()->getRepository(Employer::class)->find($idemployer);

        foreach($emploi as $id){
            $emploiWithData[] = [
                'emploi' => $emploiRepository->find($id),
                'candidat' => $candidat,

            ];
        }


        return $this->redirectToRoute('employer_showFront', [
            'items' => $emploiWithData, 'candidat' => $candidat, 'id' => $employer,
        ]);
    }

    /**
     * @Route("internshipApply/{id}", name="stage_apply")
     */
    public function applyStage($id, SessionInterface $session, StageRepository $stageRepository){
        $stage = $session->get('stage', []);
        //$stage[$id]=1;
        $stage[$id]=$stageRepository->find($id);
        $session->set('stage',$stage);
        //dd($session->get('stage'));
        return $this->redirectToRoute('stage_indexC');
    }

    /**
     * @Route("jobApply/{id}/{iduser}", name="emploi_apply")
     */
    public function applyEmploi($id, SessionInterface $session, EmploiRepository $emploiRepository, $iduser, CandidatRepository $candidatRepository){

        $emploi = $session->get('emploi', []);
        $emploi[$id]=$emploiRepository->find($id);
        $session->set('emploi',$emploi);


        dd($session->get('emploi'));

        return $this->redirectToRoute('emploi_indexC');
    }

    /**
     * @Route("internshipApply/remove/{id}", name="stage_apply_remove")
     */
    public function removeStage($id, SessionInterface $session){

        $stage = $session->get('stage', []);
        if(!empty($stage[$id])){
            unset($stage[$id]);
        }
        $session->set('stage', $stage);

        return $this->redirectToRoute('postulation_stage');
    }

    /**
     * @Route("jobApply/remove/{id}", name="emploi_apply_remove")
     */
    public function removeEmploi($id, SessionInterface $session){

        $emploi = $session->get('emploi', []);
        if(!empty($emploi[$id])){
            unset($emploi[$id]);
        }
        $session->set('emploi', $emploi);

        return $this->redirectToRoute('postulation_emploi');
    }
}
