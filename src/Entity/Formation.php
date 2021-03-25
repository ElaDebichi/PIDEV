<?php

namespace App\Entity;

use App\Repository\FormationRepository;
use Doctrine\ORM\Mapping as ORM;

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
     * @ORM\Column(type="string", length=255)
     */
    private $nomdeformateur;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $sujetdeformation;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $img;

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

    public function getNomdeformateur(): ?string
    {
        return $this->nomdeformateur;
    }

    public function setNomdeformateur(string $nomdeformateur): self
    {
        $this->nomdeformateur = $nomdeformateur;

        return $this;
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

    public function getImg(): ?string
    {
        return $this->img;
    }

    public function setImg(string $img): self
    {
        $this->img = $img;

        return $this;
    }
}
