<?php

namespace App\Repository;

use App\Entity\Emploi;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Emploi|null find($id, $lockMode = null, $lockVersion = null)
 * @method Emploi|null findOneBy(array $criteria, array $orderBy = null)
 * @method Emploi[]    findAll()
 * @method Emploi[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class EmploiRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Emploi::class);
    }

    public function updateDate(){
        return $this->getEntityManager()
            ->createQuery('DELETE FROM App\Entity\Emploi s WHERE s.dateExpiration < CURRENT_DATE()')
            ->getResult();
    }

    public function triAscLib(){
        return $this->getEntityManager()
            ->createQuery('SELECT e FROM App\Entity\Emploi e ORDER BY e.libelle ASC')
            ->getResult();
    }

    public function triDescLib(){
        return $this->getEntityManager()
            ->createQuery('SELECT e FROM App\Entity\Emploi e ORDER BY e.libelle DESC')
            ->getResult();
    }

    public function triAscId(){
        return $this->getEntityManager()
            ->createQuery('SELECT e FROM App\Entity\Emploi e ORDER BY e.id ASC')
            ->getResult();
    }

    public function triDescId(){
        return $this->getEntityManager()
            ->createQuery('SELECT e FROM App\Entity\Emploi e ORDER BY e.id DESC')
            ->getResult();
    }

    // /**
    //  * @return Emploi[] Returns an array of Emploi objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('e')
            ->andWhere('e.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('e.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Emploi
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
