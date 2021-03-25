<?php

namespace App\Form;

use App\Entity\Candidat;
use App\Entity\Skills;
use phpDocumentor\Reflection\Type;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;

class CandidatType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('password', PasswordType::class)
            ->add('phone')
            ->add('address')
            ->add('town')
            ->add('fb')
            ->add('linkdin')
            ->add('description')
            ->add('imageFile', FileType::class, [
                'mapped' => false
            ])
            ->add('nom')
            ->add('prenom')
            ->add('dateNaissance')
            ->add('nivEtude')
            ->add('typeCandidat',ChoiceType::class, [
                'choices' => ['Stagiaire' => 'Stagiaire','Job seeker'=>'Job seeker']]);
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Candidat::class,
        ]);


    }

}
