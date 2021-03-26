<?php

namespace App\Entity;

use App\Repository\StageRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=StageRepository::class)
 */
class Stage extends Offre
{

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotBlank(message="You need to insert data")
     * @Assert\Length(min="1", minMessage="Duration can be at least 3 digits",
     *     max="2", maxMessage="Duration can be up to 2 digits")
     * @Assert\Type(type="integer")
     * @Assert\Positive(message="Duration must be positive")
     */
    private $duree;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="stage")
     */
    private $user;

    public function getDuree(): ?int
    {
        return $this->duree;
    }

    public function setDuree(int $duree): self
    {
        $this->duree = $duree;

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
