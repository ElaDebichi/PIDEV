<?php

namespace App\Entity;

use App\Repository\EmployerRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
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

    /**
     * @ORM\OneToMany(targetEntity=Formation::class, mappedBy="employer")
     */
    private $formation;

    /**
     * @ORM\OneToMany(targetEntity=Evenements::class, mappedBy="employer")
     */
    private $evenements;

    /**
     * @ORM\OneToMany(targetEntity=Article::class, mappedBy="employer")
     */
    private $article;

    /**
     * @ORM\ManyToMany(targetEntity=Candidat::class, inversedBy="employers")
     */
    private $candidat;

    /**
     * @ORM\OneToMany(targetEntity=Tutor::class, mappedBy="employer")
     */
    private $tutors;


    public function __construct()
    {
        parent::__construct();
        $this->formation = new ArrayCollection();
        $this->evenements = new ArrayCollection();
        $this->article = new ArrayCollection();
        $this->candidat = new ArrayCollection();
        $this->tutors = new ArrayCollection();
    }



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

    /**
     * @return Collection|Formation[]
     */
    public function getFormation(): Collection
    {
        return $this->formation;
    }

    public function addFormation(Formation $formation): self
    {
        if (!$this->formation->contains($formation)) {
            $this->formation[] = $formation;
            $formation->setEmployer($this);
        }

        return $this;
    }

    public function removeFormation(Formation $formation): self
    {
        if ($this->formation->removeElement($formation)) {
            // set the owning side to null (unless already changed)
            if ($formation->getEmployer() === $this) {
                $formation->setEmployer(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection|Evenements[]
     */
    public function getEvenements(): Collection
    {
        return $this->evenements;
    }

    public function addEvenement(Evenements $evenement): self
    {
        if (!$this->evenements->contains($evenement)) {
            $this->evenements[] = $evenement;
            $evenement->setEmployer($this);
        }

        return $this;
    }

    public function removeEvenement(Evenements $evenement): self
    {
        if ($this->evenements->removeElement($evenement)) {
            // set the owning side to null (unless already changed)
            if ($evenement->getEmployer() === $this) {
                $evenement->setEmployer(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection|Article[]
     */
    public function getArticle(): Collection
    {
        return $this->article;
    }

    public function addArticle(Article $article): self
    {
        if (!$this->article->contains($article)) {
            $this->article[] = $article;
            $article->setEmployer($this);
        }

        return $this;
    }

    public function removeArticle(Article $article): self
    {
        if ($this->article->removeElement($article)) {
            // set the owning side to null (unless already changed)
            if ($article->getEmployer() === $this) {
                $article->setEmployer(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection|Candidat[]
     */
    public function getCandidat(): Collection
    {
        return $this->candidat;
    }

    public function addCandidat(Candidat $candidat): self
    {
        if (!$this->candidat->contains($candidat)) {
            $this->candidat[] = $candidat;
        }

        return $this;
    }

    public function removeCandidat(Candidat $candidat): self
    {
        $this->candidat->removeElement($candidat);

        return $this;
    }

    /**
     * @return Collection|Tutor[]
     */
    public function getTutors(): Collection
    {
        return $this->tutors;
    }

    public function addTutor(Tutor $tutor): self
    {
        if (!$this->tutors->contains($tutor)) {
            $this->tutors[] = $tutor;
            $tutor->setEmployer($this);
        }

        return $this;
    }

    public function removeTutor(Tutor $tutor): self
    {
        if ($this->tutors->removeElement($tutor)) {
            // set the owning side to null (unless already changed)
            if ($tutor->getEmployer() === $this) {
                $tutor->setEmployer(null);
            }
        }

        return $this;
    }




}
