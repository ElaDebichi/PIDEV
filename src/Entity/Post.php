<?php

namespace App\Entity;

use App\Repository\PostRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=PostRepository::class)
 */
class Post
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=200)
     * @Assert\NotBlank(message="Should not be blank !")
     */
    private $content;

    /**
     * @ORM\Column(type="datetime_immutable")
     *
     */
    private $date;

    /**
     * @ORM\Column(type="integer")
     */
    private $nblikes;
    /**
     * @ORM\Column(type="integer")
     */
    private $nbreports;

    /**
     * @ORM\Column(type="integer")
     */
    private $bookmarked;

    /**
     * @ORM\OneToMany(targetEntity=Comment::class, mappedBy="post", cascade={"remove"}, orphanRemoval=true)
     */
    private $comments;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="post",cascade={"persist","remove"})
     */
    private $user;

    public function __construct()
    {
        $this->comments = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getContent(): ?string
    {
        return $this->content;
    }

    public function setContent(string $content): self
    {
        $this->content = $content;

        return $this;
    }

    public function getDate(): ?\DateTimeImmutable
    {
        return $this->date;
    }

    public function setDate(\DateTimeImmutable $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getNblikes(): ?int
    {
        return $this->nblikes;
    }
    public function getBookmarked(): ?int
    {
        return $this->bookmarked;
    }
    public function setBookmarked(int $bookmarked): self
    {
        $this->bookmarked = $bookmarked;

        return $this;
    }
    public function getNbreports(): ?int
    {
        return $this->nbreports;
    }
    public function setNblikes(int $nblikes): self
    {
        $this->nblikes = $nblikes;

        return $this;
    }
    public function setNbreports(int $nbreports): self
    {
        $this->nbreports = $nbreports;

        return $this;
    }
    public function __toString()
    {
        return $this->content;
    }

    /**
     * @return Collection|Comment[]
     */
    public function getComments(): Collection
    {
        return $this->comments;
    }

    public function addComment(Comment $comment): self
    {
        if (!$this->comments->contains($comment)) {
            $this->comments[] = $comment;
            $comment->setPost($this);
        }

        return $this;
    }

    public function removeComment(Comment $comment): self
    {
        if ($this->comments->removeElement($comment)) {
            // set the owning side to null (unless already changed)
            if ($comment->getPost() === $this) {
                $comment->setPost(null);
            }
        }

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
