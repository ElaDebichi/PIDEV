<?php

namespace App\Controller;
use App\Entity\Post;
use App\Entity\Skills;
use App\Repository\PostRepository;
use Dompdf\Dompdf;
use Dompdf\Options;
use App\Entity\Candidat;
use App\Entity\Urlizer;
use App\Entity\User;
use App\Form\CandidatType;
use App\Repository\CandidatRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\EncoderFactoryInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
/**
 * @Route("/candidat")
 */
class CandidatFrontController extends AbstractController
{
    private EncoderFactoryInterface $encoder;
    private UserPasswordEncoderInterface $pwdEncoder;
    public function __construct(EncoderFactoryInterface $encoder,UserPasswordEncoderInterface $enc)
    {
        $this->encoder = $encoder;
        $this->pwdEncoder = $enc;
    }

    /**
     * @Route("/", name="candidat_front")
     */
    public function index(): Response
    {
        $candidatRepository = $this->getDoctrine()->getRepository(Candidat::class);
        return $this->render('candidat_front/index.html.twig', [
            'candidats' => $candidatRepository->findAll(),
        ]);
    }



    /**
     * @Route("/{id}", name="candidat_showFront", methods={"GET"})
     * @param $id
     * @return Response
     */
    public function show($id, PostRepository $postRepository): Response
    {

        $candidat = $this->getDoctrine()->getRepository(Candidat::class)->find($id);
        $skills = $this->getDoctrine()->getRepository(Skills::class)->findAll();
        $skillss= $candidat->getSkills();
        $posts = $postRepository->findBy( ['bookmarked' => '1'], ['date' => 'ASC']);







        return $this->render('candidat_front/show.html.twig', [
             'candidat'=>$candidat,
             'skills' => $skills,
            'skillss' => $skillss,
            'posts' => $posts,

        ]);
    }
    /**
     * @Route("/follow/{id}", name="candidat_showfollow", methods={"GET"})
     * @param $id
     * @return Response
     */
    public function showFollow($id, PostRepository $postRepository): Response
    {

        $candidat = $this->getDoctrine()->getRepository(Candidat::class)->find($id);
        $skills = $this->getDoctrine()->getRepository(Skills::class)->findAll();
        $skillss= $candidat->getSkills();
        $posts = $postRepository->findBy( ['bookmarked' => '1'], ['date' => 'ASC']);







        return $this->render('candidat_front/followers.html.twig', [
            'candidat'=>$candidat,
            'skills' => $skills,
            'skillss' => $skillss,
            'posts' => $posts,

        ]);
    }

    /**
     * @Route("/{id}/editFront", name="candidat_editFront", methods={"GET","POST"})
     * @param Request $request
     * @param $id
     * @return Response
     */
    public function edit(Request $request,$id): Response
    {
        $candidat = $this->getDoctrine()->getRepository(Candidat::class)->find($id);
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
            $encodedPassword =$encoder->encodePassword($candidat->getPassword(),$salt);
            $candidat->setPassword($this->pwdEncoder->encodePassword($candidat,$candidat->getPassword()));
            $candidat->setRoles(['ROLE_CANDIDATE']);
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('candidat_showFront' , ['id'=>$id]);
        }

        return $this->render('candidat_front/edit.html.twig', [
            'candidat' => $candidat,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="candidat_deleteFront", methods={"DELETE"})
     * @param Request $request
     * @param $id
     * @return Response
     */
    public function delete(Request $request, $id): Response
    {
        $candidat = $this->getDoctrine()->getRepository(Candidat::class)->find($id);
        if ($this->isCsrfTokenValid('delete'.$candidat->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($candidat);
            $entityManager->flush();
        }

        return $this->redirectToRoute('candidat_front');
    }

    /**
     * @Route("/recherche", name="rechercheCandidat")
     * @param Request $request
     * @return Response
     */
    public function searchAction(Request $request)
    {

        $data = $request->request->get('search');


        $em = $this->getDoctrine()->getManager();
        $query = $em->createQuery(
            'SELECT e FROM App\Entity\Candidat e
    WHERE e.town    LIKE :data')
            ->setParameter('data', '%'.$data.'%');


        $candidats = $query->getResult();

        return $this->render('candidat_front/index.html.twig', array(
            'candidats' => $candidats));

    }
    /**
     * @Route("/tri", name="triNomCandidat")
     */
    public function TriAction(Request $request)
    {




        $em = $this->getDoctrine()->getManager();
        $query = $em->createQuery(
            'SELECT e FROM App\Entity\Candidat e
    ORDER BY e.nom ASC');




        $candidats = $query->getResult();

        return $this->render('candidat_front/index.html.twig', array(
            'candidats' => $candidats));

    }
    /**
     * @Route("/tri", name="triCandidat")
     */
    public function TriActionCandidat(Request $request)
    {




        $em = $this->getDoctrine()->getManager();
        $query = $em->createQuery(
            'SELECT e FROM App\Entity\Candidat e
    where e.type_candidat = candidat ' );



        $candidats = $query->getResult();

        return $this->render('candidat_front/index.html.twig', array(
            'candidats' => $candidats));

    }
    /**
     * @Route("/tri", name="triStagiaire")
     */
    public function TriActionStagiaire(Request $request)
    {




        $em = $this->getDoctrine()->getManager();
        $query = $em->createQuery(
            'SELECT e FROM App\Entity\Candidat e
    where e.type_candidat = stagiaire ');



        $candidats = $query->getResult();

        return $this->render('candidat_front/index.html.twig', array(
            'candidats' => $candidats));

    }

    /**
     * @Route("/cv/{id}", name="candidat_cvFront", methods={"GET"})
     */
    public function showCV(Candidat $candidat): Response
    {
        // Configure Dompdf according to your needs
        $options = new Options();

        $options->set('defaultFont', 'Arial');


        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($options);




        // Retrieve the HTML generated in our twig file
        $this->renderView('candidat_front/cv.html.twig', [
            'candidat' => $candidat,
        ]);
        // Retrieve the HTML generated in our twig file
        $html="<style>
.clearfix:after {
  content: \"\";
  display: table;
  clear: both;
}
a {
  color: #001028;
  text-decoration: none;
}
body {
  font-family: Junge;
  position: relative;
  width: 21cm;  
  height: 29.7cm; 
  margin: 0 auto; 
  color: #001028;
  background: #FFFFFF; 
  font-size: 14px; 
}
.arrow {
  margin-bottom: 4px;
}
.arrow.back {
  text-align: right;
}
.inner-arrow {
  padding-right: 10px;
  height: 30px;
  display: inline-block;
  background-color: rgb(233, 125, 49);
  text-align: center;
  line-height: 30px;
  vertical-align: middle;
}
.arrow.back .inner-arrow {
  background-color: rgb(233, 217, 49);
  padding-right: 0;
  padding-left: 10px;
}
.arrow:before,
.arrow:after {
  content:'';
  display: inline-block;
  width: 0; height: 0;
  border: 15px solid transparent;
  vertical-align: middle;
}
.arrow:before {
  border-top-color: rgb(233, 125, 49);
  border-bottom-color: rgb(233, 125, 49);
  border-right-color: rgb(233, 125, 49);
}
.arrow.back:before {
  border-top-color: transparent;
  border-bottom-color: transparent;
  border-right-color: rgb(233, 217, 49);
  border-left-color: transparent;
}
.arrow:after {
  border-left-color: rgb(233, 125, 49);
}
.arrow.back:after {
  border-left-color: rgb(233, 217, 49);
  border-top-color: rgb(233, 217, 49);
  border-bottom-color: rgb(233, 217, 49);
  border-right-color: transparent;
}
.arrow span { 
  display: inline-block;
  width: 80px; 
  margin-right: 20px;
  text-align: right; 
}
.arrow.back span { 
  margin-right: 0;
  margin-left: 20px;
  text-align: left; 
}
h1 {
  color: #5D6975;
  font-family: Junge;
  font-size: 2.4em;
  line-height: 1.4em;
  font-weight: normal;
  text-align: center;
  border-top: 1px solid #5D6975;
  border-bottom: 1px solid #5D6975;
  margin: 0 0 2em 0;
}
h1 small { 
  font-size: 0.45em;
  line-height: 1.5em;
  float: left;
} 
h1 small:last-child { 
  float: right;
} 
#project { 
  float: left; 
}
#company { 
  float: right; 
}

table {
  width: 100%;
  border-collapse: collapse;
  border-spacing: 0;
  margin-bottom: 30px;
}
table th,
table td {
  text-align: center;
}
table th {
  padding: 5px 20px;
  color: #5D6975;
  border-bottom: 1px solid #C1CED9;
  white-space: nowrap;        
  font-weight: normal;
}
table .service,
table .desc {
  text-align: left;
}
table td {
  padding: 20px;
  text-align: right;
}
table td.service,
table td.desc {
  vertical-align: top;
}
table td.unit,
table td.qty,
table td.total {
  font-size: 1.2em;
}
table td.sub {
  border-top: 1px solid #C1CED9;
}
table td.grand {
  border-top: 1px solid #5D6975;
}
table tr:nth-child(2n-1) td {
  background: #EEEEEE;
}
table tr:last-child td {
  background: #DDDDDD;
}
#details {
  margin-bottom: 30px;
}
footer {
  color: #5D6975;
  width: 100%;
  height: 30px;
  position: absolute;
  bottom: 0;
  border-top: 1px solid #C1CED9;
  padding: 8px 0;
  text-align: center;
}
</style>
<h1>".$candidat->getPrenom()."  ".$candidat->getNom()." Resume</h1>
 <!DOCTYPE html>
<html lang=\"en\">
  <head>
    <meta charset=\"utf-8\">
    <title>Example 3</title>
    <link rel=\"stylesheet\" href=\"style.css\" media=\"all\" />
  </head>
  <body>
    <main>
      <table>
        <thead>
          <tr>
         
            <th class=\"desc\">DESCRIPTION</th>
            
          </tr>
        </thead><tbody>
       
         <tr><td  class=\"service\">".$candidat->getDescription()."</td></tr>
 </tbody>

      </table>
       </br></br></br></br></br>
      <div id=\"details\" class=\"clearfix\">
        <div id=\"project\">
          <span>LEVEL   :  </span>  ".$candidat->getNivEtude()."
          <br>
          <br>
           <span>EMAIL  :  </span>  ".$candidat->getAddress()."
           <br>
           <br>
            <span>PHONE  :  </span>  ".$candidat->getPhone()."
            <br>
            <br>
             <span>TYPE   :  </span>  ".$candidat->getTypeCandidat()."
             <br>
             <br>
              <span>TOWN   :  </span>  ".$candidat->getTown()."
              <br>
              <br>
               <span>FACEBOOOK  :  </span>  ".$candidat->getfB()."
               <BR>
               <br>
                <span>LINKEDIN :  </span>  ".$candidat->getLinkdin()."
               
              
          
      </div>
  
     
    </main>
    <footer>
    tabaani
    </footer>
  </body>
</html>";


        // Load HTML to Dompdf
        $dompdf->loadHtml($html);


        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (inline view)
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => false
        ]);


    }


}
