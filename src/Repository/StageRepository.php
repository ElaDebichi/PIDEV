<?php

namespace App\Repository;

use App\Entity\Stage;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Stage|null find($id, $lockMode = null, $lockVersion = null)
 * @method Stage|null findOneBy(array $criteria, array $orderBy = null)
 * @method Stage[]    findAll()
 * @method Stage[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class StageRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Stage::class);
    }

    public function updateDate(){
        return $this->getEntityManager()
            ->createQuery('DELETE FROM App\Entity\Stage s WHERE s.dateExpiration < CURRENT_DATE()')
            ->getResult();
    }

    public function triAscLib(){
        return $this->getEntityManager()
            ->createQuery('SELECT e FROM App\Entity\Stage e ORDER BY e.libelle ASC')
            ->getResult();
    }

    public function triDescLib(){
        return $this->getEntityManager()
            ->createQuery('SELECT e FROM App\Entity\Stage e ORDER BY e.libelle DESC')
            ->getResult();
    }

    public function triAscId(){
        return $this->getEntityManager()
            ->createQuery('SELECT e FROM App\Entity\Stage e ORDER BY e.id ASC')
            ->getResult();
    }

    public function triDescId(){
        return $this->getEntityManager()
            ->createQuery('SELECT e FROM App\Entity\Stage e ORDER BY e.id DESC')
            ->getResult();
    }

    // /**
    //  * @return Stage[] Returns an array of Stage objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('s')
            ->andWhere('s.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('s.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Stage
    {
        return $this->createQueryBuilder('s')
            ->andWhere('s.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
