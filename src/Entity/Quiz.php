<?php

namespace App\Entity;

use App\Repository\QuizRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=QuizRepository::class)
 */
class Quiz
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
    private $nomdequiz;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $nomdeformation;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $result;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNomdequiz(): ?string
    {
        return $this->nomdequiz;
    }

    public function setNomdequiz(string $nomdequiz): self
    {
        $this->nomdequiz = $nomdequiz;

        return $this;
    }

    public function getNomdeformation(): ?string
    {
        return $this->nomdeformation;
    }

    public function setNomdeformation(string $nomdeformation): self
    {
        $this->nomdeformation = $nomdeformation;

        return $this;
    }

    public function getResult(): ?string
    {
        return $this->result;
    }

    public function setResult(string $result): self
    {
        $this->result = $result;

        return $this;
    }
}
