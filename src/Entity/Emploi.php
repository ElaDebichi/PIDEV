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
}
