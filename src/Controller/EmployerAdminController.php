<?php

namespace App\Controller;

use App\Entity\Employer;
use App\Entity\Urlizer;
use App\Entity\User;
use App\Form\Employer1Type;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\EncoderFactoryInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

/**
 * @Route("/admin")
 */
class EmployerAdminController extends AbstractController
{
    private EncoderFactoryInterface $encoder;
    private UserPasswordEncoderInterface $pwdEncoder;
    public function __construct(EncoderFactoryInterface $encoder,UserPasswordEncoderInterface $enc)
    {
        $this->encoder = $encoder;
        $this->pwdEncoder = $enc;
    }
    /**
     * @Route("/", name="employer_admin")
     */
    public function index(): Response
    {$users = $this->getDoctrine()->getRepository(Employer::class)->findAll();
        return $this->render('employer_admin/index.html.twig', [
            'controller_name' => 'EmployerAdminController','employers' => $users
        ]);
    }

    /**
     * @Route("/new", name="employer_newAdmin", methods={"GET","POST"})
     * @param Request $request
     * @return Response
     */
    public function new(Request $request): Response
    {
            $employer = new Employer();
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



                return $this->redirectToRoute('employer_admin');
            }

            return $this->render('employer_admin/newAdmin.html.twig', [
                'employer' => $employer,
                'form' => $form->createView(),
            ]);


    }

    /**
     * @Route("/{id}/edit", name="employer_editAdmin", methods={"GET","POST"})
     * @param Request $request
     * @param $id
     * @return Response
     */
    public function edit(Request $request,$id): Response
    {
        $salt = md5(microtime());
        $employer = $this->getDoctrine()->getRepository(Employer::class)->find($id);
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

            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('employer_admin');
        }

        return $this->render('employer_admin/edit.html.twig', [
            'employer' => $employer,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="employer_showAdmin", methods={"GET"})
     * @param $id
     * @return Response
     */
    public function show($id): Response
    {
        $employer = $this->getDoctrine()->getRepository(Employer::class)->find($id);
        return $this->render('employer_admin/show.html.twig', [
            'employer' => $employer,
        ]);
    }

    /**
     * @Route("/{id}", name="employer_deleteAdmin", methods={"DELETE"})
     * @param Request $request
     * @param $id
     * @return Response
     */
    public function delete(Request $request,$id): Response
    {
        $employer = $this->getDoctrine()->getRepository(Employer::class)->find($id);
        if ($this->isCsrfTokenValid('delete' . $employer->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($employer);
            $entityManager->flush();
        }

        return $this->redirectToRoute('employer_admin');
    }
    /**
     * @Route("employer/searchStudentx ", name="searchStudentx")
     */
    public function searchStudentx(Request $request,NormalizerInterface $Normalizer)
    {
        $repository = $this->getDoctrine()->getRepository(Employer::class);
        $requestString=$request->get('searchValue');
        $employers = $repository->findEmployerByCategorie($requestString);
        $jsonContent = $Normalizer->normalize($employers, 'json',['groups'=>'employers']);
        $retour = json_encode($jsonContent);
        return new JsonResponse($jsonContent);

    }
}
