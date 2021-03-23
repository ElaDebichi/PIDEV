<?php

namespace App\Entity;

use App\Repository\EmployerRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=EmployerRepository::class)
 */
class Employer extends User
{


    /**
     * @ORM\Column(type="string", length=255)
     * @Groups({"employers"})
     */
    private $company;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups({"employers"})
     */
    private $categorie;



    public function getCompany(): ?string
    {
        return $this->company;
    }

    public function setCompany(string $company): self
    {
        $this->company = $company;

        return $this;
    }

    public function getCategorie(): ?string
    {
        return $this->categorie;
    }

    public function setCategorie(string $categorie): self
    {
        $this->categorie = $categorie;

        return $this;
    }


}
