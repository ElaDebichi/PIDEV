<?php

namespace App\Entity;

use App\Repository\UserRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Validator\Constraints as Assert;
use App\Entity\Candidat;
use App\Entity\Employer;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Gedmo\Mapping\Annotation as Gedmo;



/**
 * @ORM\Entity(repositoryClass=UserRepository::class)
 * @ORM\InheritanceType("SINGLE_TABLE")
 * @ORM\DiscriminatorColumn(name="discr", type="string")
 * @ORM\DiscriminatorMap({"employer" = "Employer","candidat" = "Candidat"})
 */
abstract class User implements UserInterface
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups({"employers","candidats"})
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=250)
     * @Assert\NotBlank(message="insert data ")
     * @Groups({"read","employers","candidats"})
     */
    private $password;

    /**
     * @ORM\Column(type="json")
     */
    private $roles = [];

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotBlank(message="insert data ")
     * @Groups({"employers","candidats"})
     */
    private $phone;

    /**
     * @Assert\NotBlank(message="Ce champs est obligatoire")
    use Symfony\Component\Validator\Constraints as Assert;
    *@Assert\Email(message="Cette adresse mail n'est pas valide ")
     * @ORM\Column(type="string", length=255)
     * @Groups({"employers","candidats"})
     */
    private $address;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="insert data ")
     * @Groups({"employers","candidats"})
     */
    private $town;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Assert\NotBlank(message="insert data ")
     * @Groups({"employers","candidats"})
     */
    private $fb;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Assert\NotBlank(message="insert data ")
     * @Groups({"employers","candidats"})
     */
    private $linkdin;

    /**
     * @ORM\Column(type="string", length=5000)
     * @Assert\NotBlank(message="insert data ")
     * @Groups({"employers","candidats"})
     */
    private $description;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Groups({"employers","candidats"})
     */
    private $img;

    /**
     * @ORM\OneToMany(targetEntity=Post::class, mappedBy="user")
     */
    private $post;

    /**
     * @ORM\OneToMany(targetEntity=Comment::class, mappedBy="user", cascade="remove")
     * @ORM\JoinColumn(name="user_id", referencedColumnName="post_id", onDelete="CASCADE")
     */
    private $comment;

    /**
     * @Gedmo\Timestampable(on="create")
     * @ORM\Column(type="datetime")
     */
    private $createdAt;

    /**
     * @Gedmo\Timestampable(on="update")
     * @ORM\Column(type="datetime")
     */
    private $updatedAt;

    /**
     * @ORM\OneToMany(targetEntity=Emploi::class, mappedBy="user")
     */
    private $emploi;

    /**
     * @ORM\OneToMany(targetEntity=Stage::class, mappedBy="user")
     */
    private $stage;

    /**
     * @ORM\ManyToMany(targetEntity=Offre::class, inversedBy="users")
     */
    private $apply;

    /**
     * @ORM\Column(type="integer")
     */
    private $nbrFollow;

    public function __construct()
    {
        $this->post = new ArrayCollection();
        $this->comment = new ArrayCollection();
        $this->emploi = new ArrayCollection();
        $this->stage = new ArrayCollection();
        $this->apply = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getPassword(): ?string
    {
        return $this->password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;

        return $this;
    }

    public function getPhone(): ?int
    {
        return $this->phone;
    }

    public function setPhone(int $phone): self
    {
        $this->phone = $phone;

        return $this;
    }

    public function getAddress(): ?string
    {
        return $this->address;
    }

    public function setAddress(string $address): self
    {
        $this->address = $address;

        return $this;
    }

    public function getTown(): ?string
    {
        return $this->town;
    }

    public function setTown(string $town): self
    {
        $this->town = $town;

        return $this;
    }

    public function getFb(): ?string
    {
        return $this->fb;
    }

    public function setFb(?string $fb): self
    {
        $this->fb = $fb;

        return $this;
    }

    public function getLinkdin(): ?string
    {
        return $this->linkdin;
    }

    public function setLinkdin(?string $linkdin): self
    {
        $this->linkdin = $linkdin;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getImg(): ?string
    {
        return $this->img;
    }

    public function setImg(?string $img): self
    {
        $this->img = $img;

        return $this;
    }

    public function eraseCredentials()
    {
        // TODO: Implement eraseCredentials() method.
    }
    public function getSalt()
    {
        // TODO: Implement getSalt() method.
    }

    public function getUsername()
    {
        return $this->getAddress();
    }

    public function getRoles(): array
    {
        $roles = $this->roles;
        // guarantee every user at least has ROLE_USER
        $roles[] = 'ROLE_USER';
        return array_unique($this->roles);
    }

    /**
     * @param string $role
     * @return $this
     */
    public function addRole(string $role): self
    {
        array_push($this->roles,$role);
        return $this;
    }

    /**
     * @param array<mixed> $roles
     * @return $this
     */
    public function setRoles(array $roles): self
    {
        $this->roles = $roles;
        return $this;
    }

    /**
     * @return Collection|Post[]
     */
    public function getPost(): Collection
    {
        return $this->post;
    }

    public function addPost(Post $post): self
    {
        if (!$this->post->contains($post)) {
            $this->post[] = $post;
            $post->setUser($this);
        }

        return $this;
    }

    public function removePost(Post $post): self
    {
        if ($this->post->removeElement($post)) {
            // set the owning side to null (unless already changed)
            if ($post->getUser() === $this) {
                $post->setUser(null);
            }
        }

        return $this;
    }
    public function __toString() : string
    {
        return $this->getAddress();

    }

    /**
     * @return Collection|Comment[]
     */
    public function getComment(): Collection
    {
        return $this->comment;
    }

    public function addComment(Comment $comment): self
    {
        if (!$this->comment->contains($comment)) {
            $this->comment[] = $comment;
            $comment->setUser($this);
        }

        return $this;
    }

    public function removeComment(Comment $comment): self
    {
        if ($this->comment->removeElement($comment)) {
            // set the owning side to null (unless already changed)
            if ($comment->getUser() === $this) {
                $comment->setUser(null);
            }
        }

        return $this;
    }

    public function getCreatedAt(): ?\DateTimeInterface
    {
        return $this->createdAt;
    }

    public function setCreatedAt(\DateTimeInterface $createdAt): self
    {
        $this->createdAt = $createdAt;

        return $this;
    }

    public function getUpdatedAt(): ?\DateTimeInterface
    {
        return $this->updatedAt;
    }

    public function setUpdatedAt(\DateTimeInterface $updatedAt): self
    {
        $this->updatedAt = $updatedAt;

        return $this;
    }

    /**
     * @return Collection|Emploi[]
     */
    public function getEmploi(): Collection
    {
        return $this->emploi;
    }

    public function addEmploi(Emploi $emploi): self
    {
        if (!$this->emploi->contains($emploi)) {
            $this->emploi[] = $emploi;
            $emploi->setUser($this);
        }

        return $this;
    }

    public function removeEmploi(Emploi $emploi): self
    {
        if ($this->emploi->removeElement($emploi)) {
            // set the owning side to null (unless already changed)
            if ($emploi->getUser() === $this) {
                $emploi->setUser(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection|Stage[]
     */
    public function getStage(): Collection
    {
        return $this->stage;
    }

    public function addStage(Stage $stage): self
    {
        if (!$this->stage->contains($stage)) {
            $this->stage[] = $stage;
            $stage->setUser($this);
        }

        return $this;
    }

    public function removeStage(Stage $stage): self
    {
        if ($this->stage->removeElement($stage)) {
            // set the owning side to null (unless already changed)
            if ($stage->getUser() === $this) {
                $stage->setUser(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection|Offre[]
     */
    public function getApply(): Collection
    {
        return $this->apply;
    }

    public function addApply(Offre $apply): self
    {
        if (!$this->apply->contains($apply)) {
            $this->apply[] = $apply;
        }

        return $this;
    }

    public function removeApply(Offre $apply): self
    {
        $this->apply->removeElement($apply);

        return $this;
    }

    public function getNbrFollow(): ?int
    {
        return $this->nbrFollow;
    }

    public function setNbrFollow(int $nbrFollow): self
    {
        $this->nbrFollow = $nbrFollow;

        return $this;
    }
}
