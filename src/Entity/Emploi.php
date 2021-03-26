<?php

namespace App\Entity;

use App\Repository\EmploiRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=EmploiRepository::class)
 */
class Emploi extends Offre
{

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="You need to insert data")
     */
    private $typeContrat;

    /**
     * @ORM\Column(type="float")
     * @Assert\NotBlank(message="You need to insert data")
     * @Assert\Length(min="3", minMessage="Salary can be at least 3 digits")
     * @Assert\Type(type="float")
     * @Assert\Positive(message="Salary must be positive")
     */
    private $salaire;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="emploi")
     */
    private $user;

    public function getTypeContrat(): ?string
    {
        return $this->typeContrat;
    }

    public function setTypeContrat(string $typeContrat): self
    {
        $this->typeContrat = $typeContrat;

        return $this;
    }

    public function getSalaire(): ?float
    {
        return $this->salaire;
    }

    public function setSalaire(float $salaire): self
    {
        $this->salaire = $salaire;

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(?User $user): self
    {
        $this->user = $user;

        return $this;
    }
}
