<?php

namespace App\Repository;

use App\Entity\Employer;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Employer|null find($id, $lockMode = null, $lockVersion = null)
 * @method Employer|null findOneBy(array $criteria, array $orderBy = null)
 * @method Employer[]    findAll()
 * @method Employer[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class EmployerRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Employer::class);
    }

     /**
      * @return Employer[] Returns an array of Employer objects
     */

    public function findByCategory($categorie)
    {
        return $this->createQueryBuilder('e')
            ->where('e.categorie = :categorie')
            ->setParameter('categorie', $categorie)
            ->getQuery()
            ->getResult()
        ;
    }

    public function findEmployerByCategorie($categorie){
        return $this->createQueryBuilder('employer')
            ->where('employer.categorie LIKE :categorie')
            ->setParameter('categorie', '%'.$categorie.'%')
            ->getQuery()
            ->getResult();
    }


    /*
    public function findOneBySomeField($value): ?Employer
    {
        return $this->createQueryBuilder('e')
            ->andWhere('e.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
