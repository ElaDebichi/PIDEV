<?php

namespace App\Controller;

use App\Entity\Post;
use App\Form\Post1Type;
use App\Repository\PostRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Config\Definition\Exception\Exception;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Constraints\DateTime;
use Symfony\Component\Validator\Exception\ConstraintDefinitionException;
use Symfony\Component\Validator\Exception\InvalidOptionsException;
use Symfony\Component\Validator\Exception\MissingOptionsException;

/**
 * @Route("/post")
 */
class PostController extends AbstractController
{
    /**
     * @Route("/", name="post_index", methods={"GET"})
     */
    public function index(PostRepository $postRepository): Response
    {
        return $this->render('post/index.html.twig', [
            'posts' => $postRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new", name="post_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $post = new Post();
        $form = $this->createForm(Post1Type::class, $post);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $post->setNblikes(0);
            $post->setNbreports(0);
            $post->setBookmarked(0);
            $post->setDate(new \DateTimeImmutable('now'));
            $entityManager->persist($post);
            $entityManager->flush();

            return $this->redirectToRoute('post_index');
        }

        return $this->render('post/new.html.twig', [
            'post' => $post,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/{id}", name="post_show", methods={"GET"})
     */
    public function show(Post $post): Response
    {
        return $this->render('post/show.html.twig', [
            'post' => $post,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="post_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Post $post): Response
    {
        $form = $this->createForm(Post1Type::class, $post);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('post_index');
        }

        return $this->render('post/edit.html.twig', [
            'post' => $post,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="post_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Post $post): Response
    {
        if ($this->isCsrfTokenValid('delete'.$post->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($post);
            $entityManager->flush();
        }

        return $this->redirectToRoute('post_index');
    }


//***************************************************************************

    public function getRealEntitiesdateonly($disponi){
        foreach ($disponi as $i => $item){
            $realEntities[$i] = ['id'=>$disponi[$i]->getId(),'content'=>$disponi[$i]->getContent(),
                'date'=>date_format($disponi[$i]->getDate(),"Y/m/d H:i:s"),'nblikes'=>$disponi[$i]->getComments()];}
        return $realEntities;
    }

    /**
     * @Route("/{content}/search", name="cxbycontent", methods={"GET","POST"})
     */
    public function contentAction($content)
    {
        $response = new JsonResponse();
        $ma=array();

        $em = $this->getDoctrine()->getManager();
        $disponi =  $em->getRepository('App\Entity\Post')->getbycontent($content);
        if(!$disponi) {
            $ma['error']= "error";
        } else {
            $ma= $this->getRealEntitiesdateonly($disponi);
        }

        $response->headers->set('Content-Type', 'application/json');
        $response->headers->set('Access-Control-Allow-Origin', '*');
        return $response->setData(array('disponibilite'=>$ma));
    }
    /**
     * @Route("/all/searchall", name="cbycontent", methods={"GET","POST"})
     */
    public function allcontentAction()
    {
        $response = new JsonResponse();
        $ma=array();

        $em = $this->getDoctrine()->getManager();
        $disponi =  $em->getRepository('App\Entity\Post')->findAll();
        if(!$disponi) {
            $ma['error']= "error";
        } else {
            $ma= $this->getRealEntitiesdateonly($disponi);
        }

        $response->headers->set('Content-Type', 'application/json');
        $response->headers->set('Access-Control-Allow-Origin', '*');
        return $response->setData(array('disponibilite'=>$ma));
    }


}
