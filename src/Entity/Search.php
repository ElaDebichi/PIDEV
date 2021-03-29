<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

class Search
{
    /**
     * @ORM\ManyToOne(targetEntity="Categorie")
     */
    private $category;


    public function getCategory(): ?Categorie
    {
        return $this->category;
    }

    public function setCategory(?Categorie $category): self
    {
        $this->category = $category;

        return $this;
    }



}