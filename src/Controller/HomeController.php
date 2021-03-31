<?php

namespace App\Controller;

use App\Entity\Candidat;
use App\Entity\Comment;
use App\Entity\Employer;
use App\Entity\Post;
use App\Entity\Urlizer;
use App\Entity\User;
use App\Form\CandidatType;
use App\Form\CommentType;
use App\Form\Employer1Type;
use App\Form\Post1Type;
use App\Repository\PostRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\EncoderFactoryInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

/**
 * @Route("/")
 */
class HomeController extends AbstractController
{
    private EncoderFactoryInterface $encoder;
    private UserPasswordEncoderInterface $pwdEncoder;
    public function __construct(EncoderFactoryInterface $encoder,UserPasswordEncoderInterface $enc)
    {
        $this->encoder = $encoder;
        $this->pwdEncoder = $enc;
    }
    /**
     * @Route("/", name="home_visitor")
     */
    public function index1()
    {
        return $this->render('homevisitor.html.twig', [

        ]);
    }

    /**
     * @Route("/user", name="home_user")
     */
    public function index2()
    {
        return $this->render('home.html.twig', [

        ]);
    }

    /**
     * @Route("/notauthorized", name="not_authorized")
     */
    public function index3()
    {
        return $this->render('notauthorized.html.twig', [

        ]);
    }



    /**
     * @Route("/newCandidat", name="candidat_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $candidat = new Candidat();
        $candidat->setNbrFollow(0);
        $salt = md5(microtime());
        $form = $this->createForm(CandidatType::class, $candidat);
        $form->handleRequest($request);
        $encoder = $this->encoder->getEncoder(User::class);
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
            $candidat->setImg($newFilename);
            $entityManager = $this->getDoctrine()->getManager();
            $encodedPassword =$encoder->encodePassword($candidat->getPassword(),$salt);
            $candidat->setPassword($this->pwdEncoder->encodePassword($candidat,$candidat->getPassword()));
            $candidat->setRoles(['ROLE_CANDIDATE']);
            $entityManager->persist($candidat);
            $entityManager->flush();

            return $this->redirectToRoute('app_login');
        }

        return $this->render('candidat_front/register.html.twig', [
            'candidat' => $candidat,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/new", name="employer_newFront", methods={"GET","POST"})
     */
    public function newEmp(Request $request): Response
    {
        $employer = new Employer();
        $employer->setNbrFollow(0);
        $salt = md5(microtime());
        $form = $this->createForm(Employer1Type::class, $employer);

        $form->handleRequest($request);
        $encoder = $this->encoder->getEncoder(User::class);

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
            $employer->setImg($newFilename);
            $entityManager = $this->getDoctrine()->getManager();
            $encodedPassword =$encoder->encodePassword($employer->getPassword(),$salt);
            $employer->setPassword($this->pwdEncoder->encodePassword($employer,$employer->getPassword()));
            $employer->setRoles(['ROLE_EMPLOYER']);
            $entityManager->persist($employer);
            $entityManager->flush();


            return $this->redirectToRoute('app_login');
        }

        return $this->render('employer_front/register.html.twig', [
            'employer' => $employer,
            'form' => $form->createView(),
        ]);


    }










}