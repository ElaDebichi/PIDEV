<?php

namespace App\Entity;

use App\Repository\EvenementsRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use ApiPlatform\Core\Annotation\ApiResource;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=EvenementsRepository::class)
 * @ORM\DiscriminatorMap({"event" = "Evenements"})
 */
class Evenements
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups({"events"})
     */
    private $id;


    /**
     * @ORM\Column(type="date")
     * @Groups({"events"})
     */
    private $Date;

    /**
     * @ORM\Column(type="string", length=50)
     * @Groups({"events"})
     */
    private $adresse;

    /**
     * @ORM\Column(type="string", length=80)
     * @Groups({"events"})
     */
    private $titre;

    /**
     * @ORM\Column(type="string", length=20)
     * @Groups({"events"})
     */
    private $nom;

    /**
     * @ORM\Column(type="integer", nullable=true)
     * @Groups({"events"})
     */
    private $nbrParticipants;

    /**
     * @ORM\Column(type="integer", nullable=true)
     * @Groups({"events"})
     */
    private $nbrMax;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups({"events"})
     */
    private $img;

    /**
     * @ORM\ManyToOne(targetEntity=Employer::class, inversedBy="evenements")
     * @Groups({"events"})
     */
    private $employer;





    public function getId(): ?int
    {
        return $this->id;
    }



    public function getDate(): ?\DateTimeInterface
    {
        return $this->Date;
    }

    public function setDate(\DateTimeInterface $Date): self
    {
        $this->Date = $Date;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getTitre(): ?string
    {
        return $this->titre;
    }

    public function setTitre(string $titre): self
    {
        $this->titre = $titre;

        return $this;
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

    public function getNbrParticipants(): ?int
    {
        return $this->nbrParticipants;
    }

    public function setNbrParticipants(?int $nbrParticipants): self
    {
        $this->nbrParticipants = $nbrParticipants;

        return $this;
    }

    public function getNbrMax(): ?int
    {
        return $this->nbrMax;
    }

    public function setNbrMax(?int $nbrMax): self
    {
        $this->nbrMax = $nbrMax;

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


}
