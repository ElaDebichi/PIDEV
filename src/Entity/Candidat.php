<?php

namespace App\Entity;

use App\Repository\CandidatRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use App\Entity\Skills;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=CandidatRepository::class)
 */
class Candidat extends User
{


    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="insert data ")
     * @Groups({"candidats"})
     */
    private $nom;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="insert data ")
     * @Groups({"candidats"})
     */
    private $prenom;

    /**
     * @ORM\Column(type="date")
     * @Groups({"candidats"})
     */
    private $dateNaissance;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="insert data ")
     * @Groups({"candidats"})
     */
    private $nivEtude;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="insert data ")
     * @Groups({"candidats"})
     */
    private $typeCandidat;

    /**
     * @ORM\ManyToMany(targetEntity=Skills::class, mappedBy="candidat")
     * @Groups({"candidats"})
     */
    private $skills;

    public function __construct()
    {
        $this->skills = new ArrayCollection();
    }





    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getPrenom(): ?string
    {
        return $this->prenom;
    }

    public function setPrenom(string $prenom): self
    {
        $this->prenom = $prenom;

        return $this;
    }

    public function getDateNaissance(): ?\DateTimeInterface
    {
        return $this->dateNaissance;
    }

    public function setDateNaissance(\DateTimeInterface $dateNaissance): self
    {
        $this->dateNaissance = $dateNaissance;

        return $this;
    }

    public function getNivEtude(): ?string
    {
        return $this->nivEtude;
    }

    public function setNivEtude(string $nivEtude): self
    {
        $this->nivEtude = $nivEtude;

        return $this;
    }

    public function getTypeCandidat(): ?string
    {
        return $this->typeCandidat;
    }

    public function setTypeCandidat(string $typeCandidat): self
    {
        $this->typeCandidat = $typeCandidat;

        return $this;
    }




    /**
     * @return Collection|Skills[]
     */
    public function getSkills(): Collection
    {
        return $this->skills;
    }

    public function addSkill(Skills $skill): self
    {
        if (!$this->skills->contains($skill)) {
            $this->skills[] = $skill;
            $skill->addCandidat($this);
        }

        return $this;
    }

    public function removeSkill(Skills $skill): self
    {
        if ($this->skills->removeElement($skill)) {
            $skill->removeCandidat($this);
        }

        return $this;
    }

}
