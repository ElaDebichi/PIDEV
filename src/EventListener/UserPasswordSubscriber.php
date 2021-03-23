<?php


namespace App\EventListener;

use Symfony\Component\EventDispatcher\EventSubscriberInterface;
use Symfony\Component\HttpKernel\Event\ViewEvent;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\HttpFoundation\Request;

use App\Entity\User;



class UserPasswordSubscriber implements EventSubscriberInterface
{
  private $passwordEncoder;

    public function __construct(UserPasswordEncoderInterface $passwordEncoder){
        $this->passwordEncoder = $passwordEncoder;
    }
    public function cryptPassword(ViewEvent $event)
    {
        $entity = $event->getControllerResult();
        $method = $event->getRequest()->getMethod();
        if($entity instanceof User && $method == Request::METHOD_POST){
            $entity->setPassword($this->passwordEncoder->encodePassword(
                $entity,
                $entity->getPassword()
            ));
        }
    }

    public static function getSubscribedEvents()
    {
        return [
            'kernel.view' => 'cryptPassword',
        ];
    }
}
