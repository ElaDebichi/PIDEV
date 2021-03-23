<?php

namespace App\Entity;

use App\Repository\SkillsRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use App\Entity\Candidat;

/**
 * @ORM\Entity(repositoryClass=SkillsRepository::class)
 */
class Skills
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
    private $libelle;



    /**
     * @ORM\ManyToMany(targetEntity=candidat::class, inversedBy="skills")
     */
    private $candidat;

    public function __construct()
    {
        $this->candidat = new ArrayCollection();
    }




    public function getId(): ?int
    {
        return $this->id;
    }

    public function getLibelle(): ?string
    {
        return $this->libelle;
    }

    public function setLibelle(string $libelle): self
    {
        $this->libelle = $libelle;

        return $this;
    }




    public function __toString()
    {
        return $this->getLibelle();

    }

    /**
     * @return Collection|candidat[]
     */
    public function getCandidat(): Collection
    {
        return $this->candidat;
    }

    public function addCandidat(candidat $candidat): self
    {
        if (!$this->candidat->contains($candidat)) {
            $this->candidat[] = $candidat;
        }

        return $this;
    }

    public function removeCandidat(candidat $candidat): self
    {
        $this->candidat->removeElement($candidat);

        return $this;
    }
}
