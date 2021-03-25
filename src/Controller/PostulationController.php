<?php

namespace App\Controller;

use App\Repository\EmploiRepository;
use App\Repository\StageRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

class PostulationController extends AbstractController
{
    /**
     * @Route("/stageApply", name="postulation_stage")
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
     * @Route("/emploiApply", name="postulation_emploi")
     */
    public function indexEmploi(SessionInterface $session, EmploiRepository $emploiRepository)
    {
        $emploi= $session->get('emploi', []);
        $emploiWithData = [];
        foreach($emploi as $id){
            $emploiWithData[] = [
                'emploi' => $emploiRepository->find($id),
            ];
        }

        //dd($emploiWithData);

        return $this->render('postulation/EmploiApply.html.twig', [
            'items' => $emploiWithData
        ]);
    }

    /**
     * @Route("stageApply/{id}", name="stage_apply")
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
     * @Route("emploiApply/{id}", name="emploi_apply")
     */
    public function applyEmploi($id, SessionInterface $session, EmploiRepository $emploiRepository){

        $emploi = $session->get('emploi', []);
        $emploi[$id]=$emploiRepository->find($id);
        $session->set('emploi',$emploi);

        //dd($session->get('emploi'));

        return $this->redirectToRoute('emploi_indexC');
    }

    /**
     * @Route("stageApply/remove/{id}", name="stage_apply_remove")
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
     * @Route("emploiApply/remove/{id}", name="emploi_apply_remove")
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
