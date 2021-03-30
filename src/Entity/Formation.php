<?php

namespace App\Entity;

use App\Repository\FormationRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use phpDocumentor\Reflection\Types\Integer;

/**
 * @ORM\Entity(repositoryClass=FormationRepository::class)
 */
class Formation
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $nomformation;


    /**
     * @ORM\Column(type="string", length=5000)
     */
    private $sujetdeformation;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $img;

    /**
     * @ORM\Column(type="integer")
     */
    private $nbrParticipants;


    /**
     * @ORM\ManyToOne(targetEntity=Employer::class, inversedBy="formation")
     */
    private $employer;

    /**
     * @ORM\ManyToOne(targetEntity=Tutor::class, inversedBy="formations")
     */
    private $tutor;

    /**
     * @ORM\ManyToMany(targetEntity=Candidat::class, inversedBy="formations")
     */
    private $participer;

    /**
     * @ORM\Column(type="integer")
     */
    private $rating;

    public function __construct()
    {
        $this->participer = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNomformation(): ?string
    {
        return $this->nomformation;
    }

    public function setNomformation(string $nomformation): self
    {
        $this->nomformation = $nomformation;

        return $this;
    }


    public function getNbrParticipants(): ?int
    {
        return $this->nbrParticipants;
    }



    public function getSujetdeformation(): ?string
    {
        return $this->sujetdeformation;
    }


    public function setSujetdeformation(string $sujetdeformation): self
    {
        $this->sujetdeformation = $sujetdeformation;

        return $this;
    }
    public function setNbrparticipants(?int $nbrParticipants): self
    {
        $this->nbrParticipants = $nbrParticipants;

        return $this;
    }

    public function getImg(): ?string
    {
        return $this->img;
    }

    public function setImg(string $img): self
    {
        $this->img = $img;

        return $this;
    }

    public function getEmployer(): ?Employer
    {
        return $this->employer;
    }

    public function setEmployer(?Employer $employer): self
    {
        $this->employer = $employer;

        return $this;
    }

    public function getTutor(): ?Tutor
    {
        return $this->tutor;
    }

    public function setTutor(?Tutor $tutor): self
    {
        $this->tutor = $tutor;

        return $this;
    }

    /**
     * @return Collection|Candidat[]
     */
    public function getParticiper(): Collection
    {
        return $this->participer;
    }

    public function addParticiper(Candidat $participer): self
    {
        if (!$this->participer->contains($participer)) {
            $this->participer[] = $participer;
        }

        return $this;
    }

    public function removeParticiper(Candidat $participer): self
    {
        $this->participer->removeElement($participer);

        return $this;
    }

    public function getRating(): ?int
    {
        return $this->rating;
    }

    public function setRating(int $rating): self
    {
        $this->rating = $rating;

        return $this;
    }
}
